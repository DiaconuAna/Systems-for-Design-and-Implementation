package catalog.web.converter;

import catalog.core.model.Contract;
import catalog.web.dto.ContractDto;
import org.springframework.stereotype.Component;

@Component
public class ContractConverter extends BaseConverter<Contract, ContractDto> {

    @Override
    public Contract convertDtoToModel(ContractDto dto) {
        var model = new Contract();
        model.setId(dto.getId());
        model.setActorId(dto.getActorId());
        model.setMovieId(dto.getMovieId());
        return model;
    }

    @Override
    public ContractDto convertModelToDto(Contract contract) {
        ContractDto dto = new ContractDto(contract.getActorId(), contract.getMovieId());
        dto.setId(contract.getId());
        return dto;
    }
}
