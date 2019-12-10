package com.rbkmoney.questionary.aggr.proxy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestResponse {

    public static String daDataParty() {
        return decode(loadFile("dadata_party_resp.json"));
    }

    public static String daDataAddress() {
        return decode(loadFile("dadata_address_resp.json"));
    }

    public static String daDataBank() {
        return decode(loadFile("dadata_bank_resp.json"));
    }

    public static String daDataFio() {
        return decode(loadFile("dadata_fio_resp.json"));
    }

    public static String daDataFmsUnit() {
        return decode(loadFile("dadata_fms_unit_resp.json"));
    }

    public static String daDataOkved() {
        return decode(loadFile("dadata_okved_resp.json"));
    }

    public static String kfEgrDetails() {
        return decode(loadFile("kf_egrDetails_resp.json"));
    }

    public static String kfEgrDetailsLegal() {
        return decode(loadFile("kf_egrDetails_legal_resp.json"));
    }

    public static String kfReqResp() {
        return decode(loadFile("kf_req_resp.json"));
    }

    public static String kfReqLegalResp() {
        return decode(loadFile("kf_req_legal_resp.json"));
    }

    public static String kfLicenseResp() {
        return decode(loadFile("kf_license_resp.json"));
    }

    public static String kfBeneficialOwnerResp() {
        return decode(loadFile("kf_beneficial_owner_resp.json"));
    }

    private static String decode(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static byte[] loadFile(String fileName) {
        final InputStream in = TestResponse.class.getClassLoader().getResourceAsStream(fileName);
        Objects.requireNonNull(in, "in can't be null");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (in; out) {
            byte[] buffer = new byte[4096];
            for (int length = in.read(buffer); length >= 0; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Exception while loading file: " + fileName, e);
        }
    }

}
