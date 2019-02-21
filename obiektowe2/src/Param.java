/**
 * This class contains information about parameter which can be measured with sensor, it's formula,name and code.
 */

public class Param {
    private String paramName;
    private String paramFormula;
    private String paramCode;
    private long idParam;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public void setParamFormula(String paramFormula) {
        this.paramFormula = paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public long getIdParam() {
        return idParam;
    }

    public void setIdParam(long idParam) {
        this.idParam = idParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Param param = (Param) o;

        if (getIdParam() != param.getIdParam()) return false;
        if (getParamName() != null ? !getParamName().equals(param.getParamName()) : param.getParamName() != null)
            return false;
        if (getParamFormula() != null ? !getParamFormula().equals(param.getParamFormula()) : param.getParamFormula() != null)
            return false;
        return getParamCode() != null ? getParamCode().equals(param.getParamCode()) : param.getParamCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getParamName() != null ? getParamName().hashCode() : 0;
        result = 31 * result + (getParamFormula() != null ? getParamFormula().hashCode() : 0);
        result = 31 * result + (getParamCode() != null ? getParamCode().hashCode() : 0);
        result = 31 * result + (int) (getIdParam() ^ (getIdParam() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return this.getParamCode();

    }
}
