package com.driftdirect.domain;

import com.driftdirect.domain.file.File;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @OneToOne
    private File flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getFlag() {
        return flag;
    }

    public void setFlag(File flag) {
        this.flag = flag;
    }
}
