package ch.zhaw.inf3.emulator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @InstructionImpl Annotation interface declaration
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InstructionImpl {
	String value();
}

/**
 * This Class is used to parse the @InstructionImpl annotations
 * and provides a mapping between mnemonics and implementation methods.
 */
class InsImplAnnotationParser {
	public Map<String, String> instruction_map;

	public void parse(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		instruction_map = new HashMap<String, String>();

		for (Method method : methods) {
			if (method.isAnnotationPresent(InstructionImpl.class)) {
				String mnemonic = method.getAnnotation(InstructionImpl.class).value();
				instruction_map.put(mnemonic, method.getName());
			}
		}
	}
}