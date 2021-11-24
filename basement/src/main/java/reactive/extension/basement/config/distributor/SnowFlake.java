package reactive.extension.basement.config.distributor;

/**
 * An ID generation strategy.
 */
class SnowFlake {

    // ==============================Fields===========================================
    /**
     * Start of stamp (2019/12/16)
     */
    private final static long START_STAMP = 1576497385000L;

    /**
     * Sequence of machine
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * BIT of machine
     */
    private final static long MACHINE_BIT = 5;

    /**
     * BIT of Data-Center
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * Support the Data-center ID, Max = 31
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    /**
     * Support the Machine-number, Max = 31
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * Generated the Mask off code, 0b111111111111=0xfff=4095
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * Left shift
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long dataCenterId;
    private final long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;


    /**
     * @param dataCenterId ...
     * @param machineId    ...
     */
    SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * Generated the next ID
     *
     * @return long
     */
    protected synchronized long nextId() {
        long currStamp = getNextStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNextStamp();
        while (mill <= lastStamp) {
            mill = getNextStamp();
        }
        return mill;
    }

    private long getNextStamp() {
        return System.currentTimeMillis();
    }
}
