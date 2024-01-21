package code.shubham.commons.generators.id.hibernate;

import code.shubham.commons.generators.id.implementations.SnowflakeSequenceIdGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class DistributedIDGenerator implements IdentifierGenerator {

	@Override
	public Object generate(final SharedSessionContractImplementor sharedSessionContractImplementor, final Object o) {
		return SnowflakeSequenceIdGenerator.getInstance().generate();
	}

}
