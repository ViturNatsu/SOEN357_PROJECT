package com.example.soen357_project;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DogData {
    private String name;
    private String age;
    private String breed;
    private String condition;
    private String day_DofB;
    private String month_DofB;
    private String year_DofB;
    private String vaccinationStatus;
    private String weight;
    private String allergies;
    private String medication;
    private String vetName;

    public DogData(String name, String age, String breed, String condition, String day_DofB, String month_DofB, String year_DofB, String dofB, String gender, String chipNumber, String vaccinationStatus, String weight, String allergies, String medication, String vetName) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.condition = condition;
        this.day_DofB = day_DofB;
        this.month_DofB = month_DofB;
        this.year_DofB = year_DofB;
        DofB = dofB;
        this.gender = gender;
        this.chipNumber = chipNumber;
        this.vaccinationStatus = vaccinationStatus;
        this.weight = weight;
        this.allergies = allergies;
        this.medication = medication;
        this.vetName = vetName;
    }

    private String DofB;
    private String gender;
    private String chipNumber;

    public DogData(String name, String age, String breed, String condition, String day_DofB, String month_DofB, String year_DofB, String dofB, String chipNumber, String vaccinationStatus, String weight, String allergies, String medication, String vetName) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.condition = condition;
        this.day_DofB = day_DofB;
        this.month_DofB = month_DofB;
        this.year_DofB = year_DofB;
        DofB = dofB;
        this.chipNumber = chipNumber;
        this.vaccinationStatus = vaccinationStatus;
        this.weight = weight;
        this.allergies = allergies;
        this.medication = medication;
        this.vetName = vetName;
    }

    public DogData() {
    }

    public DogData(String name, String age, String breed, String condition) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getCondition() {return condition;}

    public String getDay_DofB() {return day_DofB;}

    public String getMonth_DofB() {return month_DofB;}

    public String getYear_DofB() {return year_DofB;}

    public String getDofB() {return DofB;}

    public String getChipNumber() {return chipNumber;}
    public String getVaccinationStatus() {return vaccinationStatus;}
    public String getWeight() {return weight;}
    public String getAllergies() {return allergies;}
    public String getMedication() {return medication;}
    public String getVetName() {return vetName;}
    public String getGender() {return gender;}
}
