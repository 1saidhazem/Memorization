package com.example.memorization.RoomDatabase;

public class CenterGroup {

    private int idCenter;
    private int idGroup;
    private String nameCenter;
    private byte[] logoCenter;
    private String branchCenter;
    private long identificationNumberGroupSupervisor;

    public CenterGroup() {}

    public CenterGroup(int idCenter, int idGroup, String nameCenter, byte[] logoCenter, String branchCenter, long identificationNumberGroupSupervisor) {
        this.idCenter = idCenter;
        this.idGroup = idGroup;
        this.nameCenter = nameCenter;
        this.logoCenter = logoCenter;
        this.branchCenter = branchCenter;
        this.identificationNumberGroupSupervisor = identificationNumberGroupSupervisor;
    }

    public String getNameCenter() {
        return nameCenter;
    }

    public void setNameCenter(String nameCenter) {
        this.nameCenter = nameCenter;
    }

    public byte[] getLogoCenter() {
        return logoCenter;
    }

    public void setLogoCenter(byte[] logoCenter) {
        this.logoCenter = logoCenter;
    }

    public long getIdentificationNumberGroupSupervisor() {
        return identificationNumberGroupSupervisor;
    }

    public void setIdentificationNumberGroupSupervisor(long identificationNumberGroupSupervisor) {
        this.identificationNumberGroupSupervisor = identificationNumberGroupSupervisor;
    }

    public String getBranchCenter() {
        return branchCenter;
    }

    public void setBranchCenter(String branchCenter) {
        this.branchCenter = branchCenter;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }
}
