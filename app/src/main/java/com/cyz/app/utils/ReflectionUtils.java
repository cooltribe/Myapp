package com.cyz.app.utils;

import android.text.TextUtils;
import com.android.common.components.log.Logger;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by n006498 on 2015/10/22.
 */
public final class ReflectionUtils
{
    /**
     * 日志标签
     */
    private static final String TAG = "ReflectionUtils";

    /**
     * 私有构造方法
     */
    private ReflectionUtils()
    {

    }

    /**
     * Attempts to set the value of the accessible flag. Setting this flag to false will enable access checks, setting
     * to true will disable them. <BR>
     *
     * @param object Accessible Object
     * @param flag the new value for the accessible flag
     */
    public static void setAccessible(AccessibleObject object, boolean flag)
    {
        if (null != object)
        {
            object.setAccessible(flag);
        }
    }

    /**
     * Returns a Method object which represents the method matching the given name and parameter types that is declared
     * by the class represented by this Class. (Class[]) null is equivalent to the empty array. <BR>
     *
     * @param clazz class
     * @param methodName methodName
     * @param parameterTypes parameterTypes
     * @return Method
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
    {
        if (clazz == null || TextUtils.isEmpty(methodName))
        {
            Logger.warn(TAG, "getDeclaredMethod param className or methodname can not be null!");
            return null;
        }

        return getDeclaredMethodImp(clazz, methodName, parameterTypes);
    }

    private static Method getDeclaredMethodImp(Class<?> clazz, String methodName, Class<?>... parameterTypes)
    {
        Method method = null;

        try
        {
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (NoSuchMethodException e)
        {
            Logger.error(TAG, methodName, e);
        }
        catch (Exception e)
        {
            Logger.error(TAG, "getDeclaredMethod", e);
        }

        return method;
    }

    /**
     * Returns a Method object which represents the method matching the given name and parameter types that is declared
     * by the class represented by this Class. (Class[]) null is equivalent to the empty array. <BR>
     *
     * @param className className
     * @param methodName methodName
     * @param parameterTypes parameterTypes
     * @return Method
     */
    public static Method getDeclaredMethod(String className, String methodName, Class<?>... parameterTypes)
    {
        if (TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName))
        {
            Logger.warn(TAG, "getDeclaredMethod param className or methodname can not be null!");
            return null;
        }

        Method method = null;

        try
        {
            Class<?> c = Class.forName(className);
            method = getDeclaredMethodImp(c, methodName, parameterTypes);
        }
        catch (ClassNotFoundException e)
        {
            Logger.error(TAG, className, e);
        }

        return method;
    }

    private static Field getDeclaredFieldImp(Class<?> clazz, String fieldName)
    {
        Field field = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e)
        {
            Logger.error(TAG, fieldName, e);
        }
        catch (Exception e)
        {
            Logger.error(TAG, "getDeclaredField", e);
        }

        return field;
    }

    /**
     * Returns a {@code Field} object for the field with the given name which is declared in the class represented by
     * this {@code Class}.<BR>
     *
     * @param klass class
     * @param fieldName fieldName
     * @return Field
     */
    public static Field getDeclaredField(Class<?> klass, String fieldName)
    {
        if (klass == null || TextUtils.isEmpty(fieldName))
        {
            Logger.warn(TAG, "getDeclaredField param className or methodname can not be null!");
            return null;
        }

        return getDeclaredFieldImp(klass, fieldName);
    }

    /**
     * Returns a {@code Field} object for the field with the given name which is declared in the class represented by
     * this {@code Class}.<BR>
     *
     * @param className className
     * @param fieldName fieldName
     * @return Field
     */
    public static Field getDeclaredField(String className, String fieldName)
    {
        if (TextUtils.isEmpty(className) || TextUtils.isEmpty(fieldName))
        {
            Logger.warn(TAG, "getDeclaredField param className or methodname can not be null!");
            return null;
        }

        try
        {
            Class<?> c = Class.forName(className);
            return getDeclaredFieldImp(c, fieldName);
        }
        catch (ClassNotFoundException e)
        {
            Logger.error(TAG, className, e);
        }

        return null;
    }

    /**
     * Returns the result of dynamically invoking this method. Equivalent to
     * {@code receiver.methodName(arg1, arg2, ... , argN)}.
     *
     * <p>
     * If the method is static, the receiver argument is ignored (and may be null).
     *
     * <p>
     * If the method takes no arguments, you can pass {@code (Object[]) null} instead of allocating an empty array.
     *
     * <p>
     * If you're calling a varargs method, you need to pass an {@code Object[]} for the varargs parameter: that
     * conversion is usually done in {@code javac}, not the VM, and the reflection machinery does not do this for you.
     * (It couldn't, because it would be ambiguous.)
     *
     * <p>
     * Reflective method invocation follows the usual process for method lookup.
     *
     * <p>
     * If an exception is thrown during the invocation it is caught and wrapped in an InvocationTargetException. This
     * exception is then thrown.
     *
     * <p>
     * If the invocation completes normally, the return value itself is returned. If the method is declared to return a
     * primitive type, the return value is boxed. If the return type is void, null is returned.
     *
     * @param method method
     * @param receiver the object on which to call this method (or null for static methods)
     * @param args the arguments to the method
     * @return the result
     */
    public static Object invoke(Method method, Object receiver, Object... args)
    {
        if (null == method)
        {
            Logger.warn(TAG, "invoke param method can not be null!");
            return null;
        }

        try
        {
            return method.invoke(receiver, args);
        }
        catch (Exception e)
        {
            Logger.error(TAG, method + " invoke ", e);
        }
        return null;
    }
}

