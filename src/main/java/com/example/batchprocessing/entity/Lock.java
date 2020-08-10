package com.example.batchprocessing.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tbl_lock")
public class Lock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lock_name")
    private String lockName;

    @Column(name = "lock_status")
    private String lockStatus;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "last_upd_datetime")
    private Timestamp lastUpdateDatetime;

    public Lock() {
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(Timestamp lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    public Lock(String lockName, String lockStatus, Long version) {
        this.lockName = lockName;
        this.lockStatus = lockStatus;
        this.version = version;
        this.lastUpdateDatetime = new Timestamp(new Date().getTime());
    }

    @Override
    public String toString() {
        return "Lock{" +
                "lockName='" + lockName + '\'' +
                ", lockStatus='" + lockStatus + '\'' +
                ", version=" + version +
                '}';
    }
}
