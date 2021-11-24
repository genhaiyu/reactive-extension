package reactive.extension.basement.config.distributor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import reactive.extension.basement.config.DispatcherRequestCustomizer;
import reactive.extension.basement.request.RequestMessageDefinition;

/**
 * An ID generation strategy.
 *
 * @author Genhai Yu
 */
@ConditionalOnClass(SnowFlake.class)
public class DistributorRequestInitiation implements DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> {

    private final DistributorRequestProperties drp;

    public DistributorRequestInitiation(DistributorRequestProperties distributorRequestProperties) {
        this.drp = distributorRequestProperties;
    }

    @Override
    public void customize(final RequestMessageDefinition.ProduceValues produceValues) {

        if (produceValues == null) {
            throw new IllegalArgumentException("ProduceValues not initialized");
        }
        try {
            if ((drp.getDataCenterId() > 0x00 && drp.getDataCenterId() <= 31) && (drp.getMachineId() > 0x00 && drp.getMachineId() <= 31)) {
                produceValues.setMessageId(
                        String.valueOf(new SnowFlake(drp.getDataCenterId(), drp.getMachineId()).nextId())
                );

            } else {
                produceValues.setMessageId(
                        String.valueOf(new SnowFlake(0x00, 0x00).nextId())
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("The ID generation strategy has failed");
        }
    }
}
