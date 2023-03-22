package edu.ntnu.idatt2105.funn.filtering;

import java.lang.Object;
import java.time.LocalDate;

/**
 * Enum for filtering operators.
 * @see https://blog.piinalpin.com/2022/04/searching-and-filtering-using-jpa-specification/
 * @author Callum G.
 * @version 1.0 - 18.3.2023
 */
public enum FieldType {
  BOOLEAN {
    public Object parse(String value) {
      return Boolean.parseBoolean(value);
    }
  },
  STRING {
    public Object parse(String value) {
      return value;
    }
  },
  INTEGER {
    public Object parse(String value) {
      return Integer.parseInt(value);
    }
  },
  LONG {
    public Object parse(String value) {
      return Long.parseLong(value);
    }
  },
  DOUBLE {
    public Object parse(String value) {
      return Double.parseDouble(value);
    }
  },
  FLOAT {
    public Object parse(String value) {
      return Float.parseFloat(value);
    }
  },
  CHARACTER {
    public Object parse(String value) {
      return value.charAt(0);
    }
  },
  DATE {
    public Object parse(String value) {
      return LocalDate.parse(value);
    }
  };

  public abstract Object parse(String value);
}
