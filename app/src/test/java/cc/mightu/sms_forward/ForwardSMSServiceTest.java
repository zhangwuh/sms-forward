package cc.mightu.sms_forward;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForwardSMSServiceTest {

    @Test
    public void forward() {
        ForwardSMSService smsService = new ForwardSMSService();
        smsService.forward("http://192.168.50.86:8082/sms","aaa","xxx");
    }
}