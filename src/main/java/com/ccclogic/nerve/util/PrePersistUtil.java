package com.ccclogic.nerve.util;

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

public class PrePersistUtil {

    //TODO: Create two methods for updated and created events
    public static void prePersist(Object object) {
        try {

            Method[] methods = object.getClass().getMethods();

            Optional<Method> setCreatedAtMethodOp = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setCreatedAt")).findFirst();

            if (setCreatedAtMethodOp.isPresent()) {
                setCreatedAtMethodOp.get().invoke(object, new Timestamp(DateTime.now().getMillis()));
            }

            Optional<Method> setCreatedByMethodOp = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setCreatedById")).findFirst();

            if (setCreatedByMethodOp.isPresent()) {
                setCreatedByMethodOp.get().invoke(object, SecurityUtil.getLoggedInUser().getUserId());
            }

            Optional<Method> updatedAtField = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setUpdatedAt")).findFirst();
            Optional<Method> updatedByField = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setUpdatedById")).findFirst();

            if (updatedAtField.isPresent()) {
                updatedAtField.get().invoke(object, new Timestamp(DateTime.now().getMillis()));
            }

            if (updatedByField.isPresent()) {
                updatedByField.get().invoke(object, SecurityUtil.getLoggedInUser().getUserId());
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void preUpdate(Object object) {
        try {

            Method[] methods = object.getClass().getMethods();

            Optional<Method> updatedAtField = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setUpdatedAt")).findFirst();
            Optional<Method> updatedByField = Arrays.stream(methods).filter(f -> f.getName().equalsIgnoreCase("setUpdatedById")).findFirst();

            if (updatedAtField.isPresent()) {
                updatedAtField.get().invoke(object, new Timestamp(DateTime.now().getMillis()));
            }

            if (updatedByField.isPresent()) {
                updatedByField.get().invoke(object, SecurityUtil.getLoggedInUser().getUserId());
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
