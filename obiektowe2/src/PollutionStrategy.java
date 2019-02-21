/**
 * This interface and corresponding classes are used in comparation between values. It is used to return
 * difference between parameter's pollutions standard and its measured value.
 */
public interface PollutionStrategy {
    public double getPollutionMax(double value);
}
class PollutionC6H6 implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-5;
    }
}

class PollutionNO implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-40;
    }
}

class PollutionSO2 implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-125;
    }
}

class PollutionCO implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-(10000);
    }
}

class PollutionPM10 implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-50;
    }
}
class PollutionPM25 implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-25;
    }
}
class PollutionPB implements PollutionStrategy{
    public double getPollutionMax(double value){
        return value-0.5;
    }
}
class PollutionOther implements  PollutionStrategy{
    public double getPollutionMax(double value){
        return value;
    }
}


