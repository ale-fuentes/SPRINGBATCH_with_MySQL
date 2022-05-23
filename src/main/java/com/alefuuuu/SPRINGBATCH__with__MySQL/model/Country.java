package com.alefuuuu.SPRINGBATCH__with__MySQL.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    //country, latitude, longitude, name from file 'resources\countries.csv'
    @Column
    private String country;
    @Column
    private String latitude;
    @Column
    private String longitude;
    @Column
    private String name;

    @Override
    public String toString() {
        return String.format("COUNTRY >> %s (%s) coordinates latitude: %s - longitude %s",
                this.country,
                this.name,
                this.latitude,
                this.longitude
        );
    }
}
