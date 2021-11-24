package com.example.srprs.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AwsSmsTest {

    @Test
//    @Disabled
    void sendSMS() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIASFWOSMZWE5NPPL7Z", "chCy1ddhfNHBZxuRisvl7HmhtVnQ/UAkXV4DEMPD");
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard();

        AmazonSNS sns = builder.withRegion(Regions.AP_NORTHEAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        String message = "감사합니다!" +
                " 브링프라이스 상품 결제가 완료되었습니다." +
                " 문의사항은 카카오톡에서 “브링프라이스”를 검색해주세요." +
                " http://pf.kakao.com/_cyZhM";
        message = "안녕하세요? 고객님 \n저희 브링프라이스에서 고객님께 제안드리는 \"그랜드 머큐어 앰배서더 호텔 앤 레지던스 서울 용산\"은 기존 투숙객의 평점이 높은 호텔입니다. 서울 도심에서 약 2.9km에 위치하고 있는 숙소로서 아코르호텔 그룹이 국내에 최초로 선보이는 서비스드 레지던스로서 고품격 장기 숙박 호텔입니다. 환상적인 서울의 야경을 감상할 수 있는 202개의 객실을 보유하고 있습니다. 전반적으로 넓은 규모의 룸이 제공되며, 아이를 동반한 가족들의 안전과 청결을 우선시 하는 호텔의 배려로 즐겁고 행복한 시간을 보내실 수 있으리라 생각합니다.";

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();

        smsAttributes.put( "AWS.SNS.SMS.SenderID" , new MessageAttributeValue() .withStringValue("example") .withDataType("String") );
        smsAttributes.put( "AWS.SNS.SMS.MaxPrice" , new MessageAttributeValue() .withStringValue("0.5") .withDataType("String") );
//        smsAttributes.put( "AWS.SNS.SMS.SMSType" , new MessageAttributeValue() .withStringValue("Promotional") .withDataType("String") );
        smsAttributes.put( "AWS.SNS.SMS.SMSType" , new MessageAttributeValue() .withStringValue("Transactional") .withDataType("String") );

        sendSMSMessage(sns, message, "+821095933747", smsAttributes);

    }

    private void sendSMSMessage(AmazonSNS sns, String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = sns.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber).withMessageAttributes(smsAttributes));

        System.out.println(result);
    }
}
