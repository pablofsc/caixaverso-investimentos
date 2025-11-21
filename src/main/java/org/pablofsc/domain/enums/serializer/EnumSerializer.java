package org.pablofsc.domain.enums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Serializador customizado para enums que possuem método getDescricao().
 * Serializa usando a descrição em vez do nome UPPER_SNAKE_CASE do enum.
 */
public class EnumSerializer extends JsonSerializer<Enum<?>> {

  @Override
  public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value == null) {
      gen.writeNull();
      return;
    }

    try {
      Method method = value.getClass().getMethod("getDescricao");
      Object descricao = method.invoke(value);
      gen.writeString(descricao != null ? descricao.toString() : value.name());
    } catch (Exception e) {
      gen.writeString(value.name());
    }
  }
}
