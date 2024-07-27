package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.problem.Parameter;
import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JavaCodeTesterService {
    private static final Logger logger = LoggerFactory.getLogger(JavaCodeTesterService.class);

    public boolean getResult(String inputs, String output, Path filename, List<String> types) {
        try {
            byte[] bytes = Files.readAllBytes(filename);
            DynamicClassLoader classLoader = new DynamicClassLoader();
            Class<?> clazz = classLoader.defineClass(null, bytes);
            Method method = findMethod(clazz, types.size());
            if (method == null) {
                logger.error("No such method");
                return false;
            }
            List<Object> parameters = parseInputs(inputs, types);
            Object result = method.invoke(null, parameters.toArray());
            return result.toString().equals(output);
        } catch (IOException | IllegalAccessException |
                 InvocationTargetException e) {
            logger.error(e.toString());
        }
        return false;
    }

    private static Method findMethod(Class<?> clazz, int parameterCount) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getParameterCount() == parameterCount) {
                return method;
            }
        }
        return null;
    }

    private static List<Object> parseInputs(String inputs, List<String> types) {
        return Stream.of(inputs.split(","))
                .map(String::trim)
                .map(input -> parseInput(input, types.get(0)))
                .collect(Collectors.toList());
    }

    private static Object parseInput(String input, String type) {
        return switch (type) {
            case "INTEGER" -> Integer.parseInt(input);
            case "FLOAT" -> Float.parseFloat(input);
            case "DOUBLE" -> Double.parseDouble(input);
            case "CHAR", "STRING" -> input;
            default -> Long.parseLong(input);
        };
    }

    private static class DynamicClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] bytes) {
            return super.defineClass(name, bytes, 0, bytes.length);
        }
    }
}
