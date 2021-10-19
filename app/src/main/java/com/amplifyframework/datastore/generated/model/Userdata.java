package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Userdata type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Userdata", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Userdata implements Model {
  public static final QueryField ID = field("Userdata", "id");
  public static final QueryField FIRST_NAME = field("Userdata", "FirstName");
  public static final QueryField MIDDLE_NAME = field("Userdata", "MiddleName");
  public static final QueryField LAST_NAME = field("Userdata", "LastName");
  public static final QueryField MOBILE_NO = field("Userdata", "MobileNo");
  public static final QueryField EMAIL = field("Userdata", "Email");
  public static final QueryField GENDER = field("Userdata", "Gender");
  public static final QueryField AGE = field("Userdata", "Age");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String FirstName;
  private final @ModelField(targetType="String") String MiddleName;
  private final @ModelField(targetType="String", isRequired = true) String LastName;
  private final @ModelField(targetType="AWSPhone") String MobileNo;
  private final @ModelField(targetType="AWSEmail", isRequired = true) String Email;
  private final @ModelField(targetType="String") String Gender;
  private final @ModelField(targetType="Int") Integer Age;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return FirstName;
  }
  
  public String getMiddleName() {
      return MiddleName;
  }
  
  public String getLastName() {
      return LastName;
  }
  
  public String getMobileNo() {
      return MobileNo;
  }
  
  public String getEmail() {
      return Email;
  }
  
  public String getGender() {
      return Gender;
  }
  
  public Integer getAge() {
      return Age;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Userdata(String id, String FirstName, String MiddleName, String LastName, String MobileNo, String Email, String Gender, Integer Age) {
    this.id = id;
    this.FirstName = FirstName;
    this.MiddleName = MiddleName;
    this.LastName = LastName;
    this.MobileNo = MobileNo;
    this.Email = Email;
    this.Gender = Gender;
    this.Age = Age;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Userdata userdata = (Userdata) obj;
      return ObjectsCompat.equals(getId(), userdata.getId()) &&
              ObjectsCompat.equals(getFirstName(), userdata.getFirstName()) &&
              ObjectsCompat.equals(getMiddleName(), userdata.getMiddleName()) &&
              ObjectsCompat.equals(getLastName(), userdata.getLastName()) &&
              ObjectsCompat.equals(getMobileNo(), userdata.getMobileNo()) &&
              ObjectsCompat.equals(getEmail(), userdata.getEmail()) &&
              ObjectsCompat.equals(getGender(), userdata.getGender()) &&
              ObjectsCompat.equals(getAge(), userdata.getAge()) &&
              ObjectsCompat.equals(getCreatedAt(), userdata.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), userdata.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getMiddleName())
      .append(getLastName())
      .append(getMobileNo())
      .append(getEmail())
      .append(getGender())
      .append(getAge())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Userdata {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("FirstName=" + String.valueOf(getFirstName()) + ", ")
      .append("MiddleName=" + String.valueOf(getMiddleName()) + ", ")
      .append("LastName=" + String.valueOf(getLastName()) + ", ")
      .append("MobileNo=" + String.valueOf(getMobileNo()) + ", ")
      .append("Email=" + String.valueOf(getEmail()) + ", ")
      .append("Gender=" + String.valueOf(getGender()) + ", ")
      .append("Age=" + String.valueOf(getAge()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FirstNameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Userdata justId(String id) {
    return new Userdata(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      FirstName,
      MiddleName,
      LastName,
      MobileNo,
      Email,
      Gender,
      Age);
  }
  public interface FirstNameStep {
    LastNameStep firstName(String firstName);
  }
  

  public interface LastNameStep {
    EmailStep lastName(String lastName);
  }
  

  public interface EmailStep {
    BuildStep email(String email);
  }
  

  public interface BuildStep {
    Userdata build();
    BuildStep id(String id);
    BuildStep middleName(String middleName);
    BuildStep mobileNo(String mobileNo);
    BuildStep gender(String gender);
    BuildStep age(Integer age);
  }
  

  public static class Builder implements FirstNameStep, LastNameStep, EmailStep, BuildStep {
    private String id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String MiddleName;
    private String MobileNo;
    private String Gender;
    private Integer Age;
    @Override
     public Userdata build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Userdata(
          id,
          FirstName,
          MiddleName,
          LastName,
          MobileNo,
          Email,
          Gender,
          Age);
    }
    
    @Override
     public LastNameStep firstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.FirstName = firstName;
        return this;
    }
    
    @Override
     public EmailStep lastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.LastName = lastName;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        Objects.requireNonNull(email);
        this.Email = email;
        return this;
    }
    
    @Override
     public BuildStep middleName(String middleName) {
        this.MiddleName = middleName;
        return this;
    }
    
    @Override
     public BuildStep mobileNo(String mobileNo) {
        this.MobileNo = mobileNo;
        return this;
    }
    
    @Override
     public BuildStep gender(String gender) {
        this.Gender = gender;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.Age = age;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String firstName, String middleName, String lastName, String mobileNo, String email, String gender, Integer age) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .email(email)
        .middleName(middleName)
        .mobileNo(mobileNo)
        .gender(gender)
        .age(age);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder middleName(String middleName) {
      return (CopyOfBuilder) super.middleName(middleName);
    }
    
    @Override
     public CopyOfBuilder mobileNo(String mobileNo) {
      return (CopyOfBuilder) super.mobileNo(mobileNo);
    }
    
    @Override
     public CopyOfBuilder gender(String gender) {
      return (CopyOfBuilder) super.gender(gender);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
  }
  
}
