package digit.validators;

import digit.repository.BirthRegistrationRepository;
import digit.util.MdmsUtil;
import digit.web.models.BirthApplicationSearchCriteria;
import digit.web.models.BirthRegistrationApplication;
import digit.web.models.BirthRegistrationRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BirthApplicationValidator {

    @Autowired
    private BirthRegistrationRepository repository;

    @Autowired
    private MdmsUtil mdmsUtil;

    public void validateBirthApplication(BirthRegistrationRequest birthRegistrationRequest) {


         // Fetch master data from MDMS
            // Integer registrationCharges = mdmsUtil.fetchRegistrationChargesFromMdms(
            //     birthRegistrationRequest.getRequestInfo(),
            //     birthRegistrationRequest.getBirthRegistrationApplications().get(0).getTenantId()
            // );

            // log.info("Registration Charges for Validation: " + registrationCharges);


        birthRegistrationRequest.getBirthRegistrationApplications().forEach(application -> {
            if(ObjectUtils.isEmpty(application.getTenantId()))
                throw new CustomException("EG_BT_APP_ERR", "tenantId is mandatory for creating birth registration applications");
        });
    }

    public BirthRegistrationApplication validateApplicationExistence(BirthRegistrationApplication birthRegistrationApplication) {
        return repository.getApplications(BirthApplicationSearchCriteria.builder().applicationNumber(birthRegistrationApplication.getApplicationNumber()).build()).get(0);
    }
}