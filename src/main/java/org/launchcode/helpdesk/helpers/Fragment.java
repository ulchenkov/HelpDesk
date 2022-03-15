package org.launchcode.helpdesk.helpers;

public class Fragment {

    private String file;
    private String fragmentName;

    public Fragment(String file, String fragmentName) {
        this.file = file;
        this.fragmentName = fragmentName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
