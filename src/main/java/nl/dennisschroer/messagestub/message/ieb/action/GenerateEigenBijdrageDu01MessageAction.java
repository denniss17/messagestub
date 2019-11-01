package nl.dennisschroer.messagestub.message.ieb.action;

import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.exchange.ggk.action.AbstractGenerateDu01MessageAction;
import nl.dennisschroer.messagestub.message.ieb.IEbConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class GenerateEigenBijdrageDu01MessageAction extends AbstractGenerateDu01MessageAction {
    protected GenerateEigenBijdrageDu01MessageAction(ExchangeMessageService exchangeMessageService) {
        super(exchangeMessageService);
    }

    @Override
    public boolean isApplicableToMessageType(@NotNull String messageType) {
        return Arrays.asList("WMO402", "WMO404").contains(messageType);
    }

    @Override
    protected String getFunctieVersie() {
        return StringUtils.leftPad(String.valueOf(IEbConstants.VERSIE), 4, '0');
    }

    @Override
    protected String getFunctieSubversie() {
        return StringUtils.leftPad(String.valueOf(IEbConstants.SUBVERSIE), 4, '0');
    }
}
