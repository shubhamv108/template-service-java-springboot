package code.shubham.commons.generators.id.implementations;

import code.shubham.commons.generators.id.IDGenerator;

import java.time.Instant;

import static code.shubham.commons.generators.id.constants.Constants.NODE_ID_BIT_LENGTH;
import static code.shubham.commons.generators.id.constants.Constants.SEQUENCE_BIT_LENGTH;

public class SnowflakeSequenceIdGenerator implements IDGenerator<Long> {

	private final Long nodeId;

	private final long maxSequence = -1L ^ (-1L << SEQUENCE_BIT_LENGTH);

	private final long maxNodeIdValue = -1L ^ (-1L << NODE_ID_BIT_LENGTH);

	private final long EPOCH_START = Instant.EPOCH.toEpochMilli();

	private final long timeStampLeftSift = NODE_ID_BIT_LENGTH + SEQUENCE_BIT_LENGTH;

	private volatile long currentSequence = 0L;

	private volatile long lastTimestamp = -1L;

	public static SnowflakeSequenceIdGenerator getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static final class SingletonHolder {

		public static final SnowflakeSequenceIdGenerator INSTANCE = new SnowflakeSequenceIdGenerator(
				new NodeIdGenerator().generate());

	}

	public SnowflakeSequenceIdGenerator(final long nodeId) {
		this.isNodeIdValidOrThrowException(nodeId);
		this.nodeId = nodeId;
	}

	private void isNodeIdValidOrThrowException(final long nodeId) {
		if (nodeId > this.maxNodeIdValue || nodeId < 0) {
			throw new IllegalArgumentException(
					String.format("Node Id can't be greater than %d or less than 0", this.maxNodeIdValue));
		}
	}

	@Override
	public Long generate() {
		synchronized (this) {
			long currentTimeStamp = getTimeStamp();
			if (currentTimeStamp < this.lastTimestamp)
				throw new RuntimeException(
						String.format("Clock moved backwards. Refusing to generate id for %s milliseconds",
								(this.lastTimestamp - currentTimeStamp)));

			if (this.lastTimestamp == currentTimeStamp) {
				this.currentSequence = (this.currentSequence + 1) & maxSequence;
				if (this.currentSequence == 0) {
					currentTimeStamp = this.waitTillNextMillis();
				}
			}
			else {
				this.currentSequence = 0;
			}
			this.lastTimestamp = currentTimeStamp;
			return ((currentTimeStamp - EPOCH_START) << timeStampLeftSift) | (this.nodeId << SEQUENCE_BIT_LENGTH)
					| this.currentSequence;
		}
	}

	private long getTimeStamp() {
		return System.currentTimeMillis();
	}

	private long waitTillNextMillis() {
		var currentTimeStamp = this.getTimeStamp();
		while (currentTimeStamp <= this.lastTimestamp)
			currentTimeStamp = this.getTimeStamp();

		return currentTimeStamp;
	}

	public static void main(String[] args) {
		SnowflakeSequenceIdGenerator generator = new SnowflakeSequenceIdGenerator(new NodeIdGenerator().generate());
		System.out.println(generator.generate());
	}

}