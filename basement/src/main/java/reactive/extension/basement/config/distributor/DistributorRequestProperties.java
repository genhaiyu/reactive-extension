package reactive.extension.basement.config.distributor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * An ID generation strategy.
 *
 * @author Genhai Yu
 */
@ConfigurationProperties(prefix = "reactive.extension.distribute")
public class DistributorRequestProperties {

    /**
     * dataCenterId (0~31)
     * <p>
     * {@link SnowFlake#SnowFlake(long datacenterId, long machineId)}
     */
    private int dataCenterId;

    /**
     * machineId (0~31)
     * <p>
     * {@link SnowFlake#SnowFlake(long datacenterId, long machineId)}
     */
    private int machineId;

    public int getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
