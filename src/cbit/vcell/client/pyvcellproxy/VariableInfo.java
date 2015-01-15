/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cbit.vcell.client.pyvcellproxy;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-1-15")
public class VariableInfo implements org.apache.thrift.TBase<VariableInfo, VariableInfo._Fields>, java.io.Serializable, Cloneable, Comparable<VariableInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VariableInfo");

  private static final org.apache.thrift.protocol.TField VARIABLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("variableName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DOMAIN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("domainName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VARIABLE_DOMAIN_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("variableDomainType", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField UNITS_LABEL_FIELD_DESC = new org.apache.thrift.protocol.TField("unitsLabel", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new VariableInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new VariableInfoTupleSchemeFactory());
  }

  public String variableName; // required
  public String domainName; // optional
  public String variableDomainType; // optional
  public String unitsLabel; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VARIABLE_NAME((short)1, "variableName"),
    DOMAIN_NAME((short)2, "domainName"),
    VARIABLE_DOMAIN_TYPE((short)3, "variableDomainType"),
    UNITS_LABEL((short)4, "unitsLabel");

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
        case 1: // VARIABLE_NAME
          return VARIABLE_NAME;
        case 2: // DOMAIN_NAME
          return DOMAIN_NAME;
        case 3: // VARIABLE_DOMAIN_TYPE
          return VARIABLE_DOMAIN_TYPE;
        case 4: // UNITS_LABEL
          return UNITS_LABEL;
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

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.DOMAIN_NAME,_Fields.VARIABLE_DOMAIN_TYPE,_Fields.UNITS_LABEL};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VARIABLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("variableName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DOMAIN_NAME, new org.apache.thrift.meta_data.FieldMetaData("domainName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VARIABLE_DOMAIN_TYPE, new org.apache.thrift.meta_data.FieldMetaData("variableDomainType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UNITS_LABEL, new org.apache.thrift.meta_data.FieldMetaData("unitsLabel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VariableInfo.class, metaDataMap);
  }

  public VariableInfo() {
  }

  public VariableInfo(
    String variableName)
  {
    this();
    this.variableName = variableName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VariableInfo(VariableInfo other) {
    if (other.isSetVariableName()) {
      this.variableName = other.variableName;
    }
    if (other.isSetDomainName()) {
      this.domainName = other.domainName;
    }
    if (other.isSetVariableDomainType()) {
      this.variableDomainType = other.variableDomainType;
    }
    if (other.isSetUnitsLabel()) {
      this.unitsLabel = other.unitsLabel;
    }
  }

  public VariableInfo deepCopy() {
    return new VariableInfo(this);
  }

  @Override
  public void clear() {
    this.variableName = null;
    this.domainName = null;
    this.variableDomainType = null;
    this.unitsLabel = null;
  }

  public String getVariableName() {
    return this.variableName;
  }

  public VariableInfo setVariableName(String variableName) {
    this.variableName = variableName;
    return this;
  }

  public void unsetVariableName() {
    this.variableName = null;
  }

  /** Returns true if field variableName is set (has been assigned a value) and false otherwise */
  public boolean isSetVariableName() {
    return this.variableName != null;
  }

  public void setVariableNameIsSet(boolean value) {
    if (!value) {
      this.variableName = null;
    }
  }

  public String getDomainName() {
    return this.domainName;
  }

  public VariableInfo setDomainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  public void unsetDomainName() {
    this.domainName = null;
  }

  /** Returns true if field domainName is set (has been assigned a value) and false otherwise */
  public boolean isSetDomainName() {
    return this.domainName != null;
  }

  public void setDomainNameIsSet(boolean value) {
    if (!value) {
      this.domainName = null;
    }
  }

  public String getVariableDomainType() {
    return this.variableDomainType;
  }

  public VariableInfo setVariableDomainType(String variableDomainType) {
    this.variableDomainType = variableDomainType;
    return this;
  }

  public void unsetVariableDomainType() {
    this.variableDomainType = null;
  }

  /** Returns true if field variableDomainType is set (has been assigned a value) and false otherwise */
  public boolean isSetVariableDomainType() {
    return this.variableDomainType != null;
  }

  public void setVariableDomainTypeIsSet(boolean value) {
    if (!value) {
      this.variableDomainType = null;
    }
  }

  public String getUnitsLabel() {
    return this.unitsLabel;
  }

  public VariableInfo setUnitsLabel(String unitsLabel) {
    this.unitsLabel = unitsLabel;
    return this;
  }

  public void unsetUnitsLabel() {
    this.unitsLabel = null;
  }

  /** Returns true if field unitsLabel is set (has been assigned a value) and false otherwise */
  public boolean isSetUnitsLabel() {
    return this.unitsLabel != null;
  }

  public void setUnitsLabelIsSet(boolean value) {
    if (!value) {
      this.unitsLabel = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VARIABLE_NAME:
      if (value == null) {
        unsetVariableName();
      } else {
        setVariableName((String)value);
      }
      break;

    case DOMAIN_NAME:
      if (value == null) {
        unsetDomainName();
      } else {
        setDomainName((String)value);
      }
      break;

    case VARIABLE_DOMAIN_TYPE:
      if (value == null) {
        unsetVariableDomainType();
      } else {
        setVariableDomainType((String)value);
      }
      break;

    case UNITS_LABEL:
      if (value == null) {
        unsetUnitsLabel();
      } else {
        setUnitsLabel((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VARIABLE_NAME:
      return getVariableName();

    case DOMAIN_NAME:
      return getDomainName();

    case VARIABLE_DOMAIN_TYPE:
      return getVariableDomainType();

    case UNITS_LABEL:
      return getUnitsLabel();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VARIABLE_NAME:
      return isSetVariableName();
    case DOMAIN_NAME:
      return isSetDomainName();
    case VARIABLE_DOMAIN_TYPE:
      return isSetVariableDomainType();
    case UNITS_LABEL:
      return isSetUnitsLabel();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VariableInfo)
      return this.equals((VariableInfo)that);
    return false;
  }

  public boolean equals(VariableInfo that) {
    if (that == null)
      return false;

    boolean this_present_variableName = true && this.isSetVariableName();
    boolean that_present_variableName = true && that.isSetVariableName();
    if (this_present_variableName || that_present_variableName) {
      if (!(this_present_variableName && that_present_variableName))
        return false;
      if (!this.variableName.equals(that.variableName))
        return false;
    }

    boolean this_present_domainName = true && this.isSetDomainName();
    boolean that_present_domainName = true && that.isSetDomainName();
    if (this_present_domainName || that_present_domainName) {
      if (!(this_present_domainName && that_present_domainName))
        return false;
      if (!this.domainName.equals(that.domainName))
        return false;
    }

    boolean this_present_variableDomainType = true && this.isSetVariableDomainType();
    boolean that_present_variableDomainType = true && that.isSetVariableDomainType();
    if (this_present_variableDomainType || that_present_variableDomainType) {
      if (!(this_present_variableDomainType && that_present_variableDomainType))
        return false;
      if (!this.variableDomainType.equals(that.variableDomainType))
        return false;
    }

    boolean this_present_unitsLabel = true && this.isSetUnitsLabel();
    boolean that_present_unitsLabel = true && that.isSetUnitsLabel();
    if (this_present_unitsLabel || that_present_unitsLabel) {
      if (!(this_present_unitsLabel && that_present_unitsLabel))
        return false;
      if (!this.unitsLabel.equals(that.unitsLabel))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_variableName = true && (isSetVariableName());
    list.add(present_variableName);
    if (present_variableName)
      list.add(variableName);

    boolean present_domainName = true && (isSetDomainName());
    list.add(present_domainName);
    if (present_domainName)
      list.add(domainName);

    boolean present_variableDomainType = true && (isSetVariableDomainType());
    list.add(present_variableDomainType);
    if (present_variableDomainType)
      list.add(variableDomainType);

    boolean present_unitsLabel = true && (isSetUnitsLabel());
    list.add(present_unitsLabel);
    if (present_unitsLabel)
      list.add(unitsLabel);

    return list.hashCode();
  }

  @Override
  public int compareTo(VariableInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetVariableName()).compareTo(other.isSetVariableName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVariableName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.variableName, other.variableName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDomainName()).compareTo(other.isSetDomainName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDomainName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.domainName, other.domainName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVariableDomainType()).compareTo(other.isSetVariableDomainType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVariableDomainType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.variableDomainType, other.variableDomainType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUnitsLabel()).compareTo(other.isSetUnitsLabel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUnitsLabel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.unitsLabel, other.unitsLabel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("VariableInfo(");
    boolean first = true;

    sb.append("variableName:");
    if (this.variableName == null) {
      sb.append("null");
    } else {
      sb.append(this.variableName);
    }
    first = false;
    if (isSetDomainName()) {
      if (!first) sb.append(", ");
      sb.append("domainName:");
      if (this.domainName == null) {
        sb.append("null");
      } else {
        sb.append(this.domainName);
      }
      first = false;
    }
    if (isSetVariableDomainType()) {
      if (!first) sb.append(", ");
      sb.append("variableDomainType:");
      if (this.variableDomainType == null) {
        sb.append("null");
      } else {
        sb.append(this.variableDomainType);
      }
      first = false;
    }
    if (isSetUnitsLabel()) {
      if (!first) sb.append(", ");
      sb.append("unitsLabel:");
      if (this.unitsLabel == null) {
        sb.append("null");
      } else {
        sb.append(this.unitsLabel);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (variableName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'variableName' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class VariableInfoStandardSchemeFactory implements SchemeFactory {
    public VariableInfoStandardScheme getScheme() {
      return new VariableInfoStandardScheme();
    }
  }

  private static class VariableInfoStandardScheme extends StandardScheme<VariableInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VariableInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VARIABLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.variableName = iprot.readString();
              struct.setVariableNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DOMAIN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.domainName = iprot.readString();
              struct.setDomainNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VARIABLE_DOMAIN_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.variableDomainType = iprot.readString();
              struct.setVariableDomainTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // UNITS_LABEL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.unitsLabel = iprot.readString();
              struct.setUnitsLabelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, VariableInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.variableName != null) {
        oprot.writeFieldBegin(VARIABLE_NAME_FIELD_DESC);
        oprot.writeString(struct.variableName);
        oprot.writeFieldEnd();
      }
      if (struct.domainName != null) {
        if (struct.isSetDomainName()) {
          oprot.writeFieldBegin(DOMAIN_NAME_FIELD_DESC);
          oprot.writeString(struct.domainName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.variableDomainType != null) {
        if (struct.isSetVariableDomainType()) {
          oprot.writeFieldBegin(VARIABLE_DOMAIN_TYPE_FIELD_DESC);
          oprot.writeString(struct.variableDomainType);
          oprot.writeFieldEnd();
        }
      }
      if (struct.unitsLabel != null) {
        if (struct.isSetUnitsLabel()) {
          oprot.writeFieldBegin(UNITS_LABEL_FIELD_DESC);
          oprot.writeString(struct.unitsLabel);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VariableInfoTupleSchemeFactory implements SchemeFactory {
    public VariableInfoTupleScheme getScheme() {
      return new VariableInfoTupleScheme();
    }
  }

  private static class VariableInfoTupleScheme extends TupleScheme<VariableInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VariableInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.variableName);
      BitSet optionals = new BitSet();
      if (struct.isSetDomainName()) {
        optionals.set(0);
      }
      if (struct.isSetVariableDomainType()) {
        optionals.set(1);
      }
      if (struct.isSetUnitsLabel()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetDomainName()) {
        oprot.writeString(struct.domainName);
      }
      if (struct.isSetVariableDomainType()) {
        oprot.writeString(struct.variableDomainType);
      }
      if (struct.isSetUnitsLabel()) {
        oprot.writeString(struct.unitsLabel);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VariableInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.variableName = iprot.readString();
      struct.setVariableNameIsSet(true);
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.domainName = iprot.readString();
        struct.setDomainNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.variableDomainType = iprot.readString();
        struct.setVariableDomainTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.unitsLabel = iprot.readString();
        struct.setUnitsLabelIsSet(true);
      }
    }
  }

}

