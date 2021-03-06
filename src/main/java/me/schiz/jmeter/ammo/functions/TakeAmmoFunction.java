package me.schiz.jmeter.ammo.functions;

import me.schiz.jmeter.ammo.Cartridge;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TakeAmmoFunction extends AbstractFunction {
    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__takeAmmo";

    static {
        desc.add("Cartridge name");
        desc.add("Name of variable in which to store the result (optional)");
    }
    private Object[] values;

    public TakeAmmoFunction() {
    }

    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        JMeterVariables vars = getVariables();
        String cartridgeName=((CompoundVariable) values[0]).execute();
        String result = Cartridge.take(cartridgeName);

        if (vars != null && values.length>1) {
            String varName = ((CompoundVariable) values[1]).execute().trim();
            vars.put(varName, result);
        }
        return result;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 1);
        values = parameters.toArray();
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    public List<String> getArgumentDesc() {
        return desc;
    }
}