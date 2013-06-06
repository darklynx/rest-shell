package org.springframework.data.rest.shell.formatter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * @author Jon Brisbin
 */
public class JsonFormatter extends FormatterSupport {

  private static final List<String> SUPPORTED = Arrays.asList("json");
  private final        Charset utf8           = Charset.forName("UTF-8");
  private final        ObjectMapper mapper    = new ObjectMapper();

  {
    mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
  }

  @Override public Collection<String> getSupportedList() {
    return SUPPORTED;
  }

  @Override public String format(String nonFormattedString) {
    Object obj;
    try {
      if(nonFormattedString.startsWith("[")) {
        obj = mapper.readValue(nonFormattedString.getBytes(utf8), List.class);
      } else {
        obj = mapper.readValue(nonFormattedString.getBytes(utf8), Map.class);
      }
      return mapper.writeValueAsString(obj);
    } catch(IOException e) {
      throw new HttpMessageNotReadableException(e.getMessage(), e);
    }
  }

}
