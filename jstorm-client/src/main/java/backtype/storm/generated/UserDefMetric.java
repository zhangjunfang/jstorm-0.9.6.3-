/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package backtype.storm.generated;

import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class UserDefMetric implements org.apache.thrift7.TBase<UserDefMetric, UserDefMetric._Fields>, java.io.Serializable, Cloneable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 3829050059348341614L;

private static final org.apache.thrift7.protocol.TStruct STRUCT_DESC = new org.apache.thrift7.protocol.TStruct("UserDefMetric");

  private static final org.apache.thrift7.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift7.protocol.TField("type", org.apache.thrift7.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift7.protocol.TField NAME_FIELD_DESC = new org.apache.thrift7.protocol.TField("name", org.apache.thrift7.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift7.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift7.protocol.TField("value", org.apache.thrift7.protocol.TType.DOUBLE, (short)3);

  private String type; // required
  private String name; // required
  private double value; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift7.TFieldIdEnum {
    TYPE((short)1, "type"),
    NAME((short)2, "name"),
    VALUE((short)3, "value");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TYPE
          return TYPE;
        case 2: // NAME
          return NAME;
        case 3: // VALUE
          return VALUE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    @Override
	public short getThriftFieldId() {
      return _thriftId;
    }

    @Override
	public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VALUE_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift7.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift7.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift7.meta_data.FieldMetaData("type", org.apache.thrift7.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift7.meta_data.FieldValueMetaData(org.apache.thrift7.protocol.TType.STRING)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift7.meta_data.FieldMetaData("name", org.apache.thrift7.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift7.meta_data.FieldValueMetaData(org.apache.thrift7.protocol.TType.STRING)));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift7.meta_data.FieldMetaData("value", org.apache.thrift7.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift7.meta_data.FieldValueMetaData(org.apache.thrift7.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift7.meta_data.FieldMetaData.addStructMetaDataMap(UserDefMetric.class, metaDataMap);
  }

  public UserDefMetric() {
  }

  public UserDefMetric(
    String type,
    String name,
    double value)
  {
    this();
    this.type = type;
    this.name = name;
    this.value = value;
    set_value_isSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserDefMetric(UserDefMetric other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.is_set_type()) {
      this.type = other.type;
    }
    if (other.is_set_name()) {
      this.name = other.name;
    }
    this.value = other.value;
  }

  @Override
public UserDefMetric deepCopy() {
    return new UserDefMetric(this);
  }

  @Override
  public void clear() {
    this.type = null;
    this.name = null;
    set_value_isSet(false);
    this.value = 0.0;
  }

  public String get_type() {
    return this.type;
  }

  public void set_type(String type) {
    this.type = type;
  }

  public void unset_type() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean is_set_type() {
    return this.type != null;
  }

  public void set_type_isSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String get_name() {
    return this.name;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public void unset_name() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean is_set_name() {
    return this.name != null;
  }

  public void set_name_isSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public double get_value() {
    return this.value;
  }

  public void set_value(double value) {
    this.value = value;
    set_value_isSet(true);
  }

  public void unset_value() {
    __isset_bit_vector.clear(__VALUE_ISSET_ID);
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean is_set_value() {
    return __isset_bit_vector.get(__VALUE_ISSET_ID);
  }

  public void set_value_isSet(boolean value) {
    __isset_bit_vector.set(__VALUE_ISSET_ID, value);
  }

  @Override
public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unset_type();
      } else {
        set_type((String)value);
      }
      break;

    case NAME:
      if (value == null) {
        unset_name();
      } else {
        set_name((String)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unset_value();
      } else {
        set_value((Double)value);
      }
      break;

    }
  }

  @Override
public Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return get_type();

    case NAME:
      return get_name();

    case VALUE:
      return Double.valueOf(get_value());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  @Override
public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TYPE:
      return is_set_type();
    case NAME:
      return is_set_name();
    case VALUE:
      return is_set_value();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserDefMetric)
      return this.equals((UserDefMetric)that);
    return false;
  }

  public boolean equals(UserDefMetric that) {
    if (that == null)
      return false;

    boolean this_present_type = true && this.is_set_type();
    boolean that_present_type = true && that.is_set_type();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_name = true && this.is_set_name();
    boolean that_present_name = true && that.is_set_name();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_value = true;
    boolean that_present_value = true;
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (this.value != that.value)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_type = true && (is_set_type());
    builder.append(present_type);
    if (present_type)
      builder.append(type);

    boolean present_name = true && (is_set_name());
    builder.append(present_name);
    if (present_name)
      builder.append(name);

    boolean present_value = true;
    builder.append(present_value);
    if (present_value)
      builder.append(value);

    return builder.toHashCode();
  }

  @Override
public int compareTo(UserDefMetric other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    UserDefMetric typedOther = other;

    lastComparison = Boolean.valueOf(is_set_type()).compareTo(typedOther.is_set_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_type()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_name()).compareTo(typedOther.is_set_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_name()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_value()).compareTo(typedOther.is_set_value());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_value()) {
      lastComparison = org.apache.thrift7.TBaseHelper.compareTo(this.value, typedOther.value);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @Override
public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  @Override
public void read(org.apache.thrift7.protocol.TProtocol iprot) throws org.apache.thrift7.TException {
    org.apache.thrift7.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift7.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // TYPE
          if (field.type == org.apache.thrift7.protocol.TType.STRING) {
            this.type = iprot.readString();
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // NAME
          if (field.type == org.apache.thrift7.protocol.TType.STRING) {
            this.name = iprot.readString();
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // VALUE
          if (field.type == org.apache.thrift7.protocol.TType.DOUBLE) {
            this.value = iprot.readDouble();
            set_value_isSet(true);
          } else { 
            org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift7.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();
    validate();
  }

  @Override
public void write(org.apache.thrift7.protocol.TProtocol oprot) throws org.apache.thrift7.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.type != null) {
      oprot.writeFieldBegin(TYPE_FIELD_DESC);
      oprot.writeString(this.type);
      oprot.writeFieldEnd();
    }
    if (this.name != null) {
      oprot.writeFieldBegin(NAME_FIELD_DESC);
      oprot.writeString(this.name);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(VALUE_FIELD_DESC);
    oprot.writeDouble(this.value);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("UserDefMetric(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    sb.append(this.value);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift7.TException {
    // check for required fields
    if (!is_set_type()) {
      throw new org.apache.thrift7.protocol.TProtocolException("Required field 'type' is unset! Struct:" + toString());
    }

    if (!is_set_name()) {
      throw new org.apache.thrift7.protocol.TProtocolException("Required field 'name' is unset! Struct:" + toString());
    }

    if (!is_set_value()) {
      throw new org.apache.thrift7.protocol.TProtocolException("Required field 'value' is unset! Struct:" + toString());
    }

  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift7.protocol.TCompactProtocol(new org.apache.thrift7.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift7.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift7.protocol.TCompactProtocol(new org.apache.thrift7.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift7.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

