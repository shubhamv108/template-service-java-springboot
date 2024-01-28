package code.shubham.core.inventorycommons;

import org.springframework.transaction.annotation.Transactional;

public interface IInventoryService {

	@Transactional
	boolean applyQuantityOperation(Long id, int quantity, String referenceId);

	@Transactional
	boolean revertQuantityOperation(String referenceId);

	boolean hasQuantity(Long id, int quantity);

}
