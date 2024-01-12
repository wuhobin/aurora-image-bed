package com.wuhobin.common.util.security;



import com.wuhobin.common.exception.SM2Exception;
import com.wuhobin.common.exception.SM4DecryptException;
import com.wuhobin.common.exception.SM4EncryptException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * 行外国密基本逻辑，仅供参考
 */
@Slf4j
public class SMUtil {

    private static final String CHARSET_ENCODING = "UTF-8";

    private static final String UserId = "1234567812345678";

    private final static int RS_LEN = 32;

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");

    private static final BigInteger gx = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);

    private static final BigInteger gy = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);

    //public static final ECPoint G_POINT = x9ECParameters.getCurve().createPoint(gx, gy);

    //public static final ECDomainParameters DOMAIN_PARAMS_FROM_XY = new ECDomainParameters(x9ECParameters.getCurve(), G_POINT,
    //        x9ECParameters.getCurve().getOrder(), x9ECParameters.getCurve().getCofactor());

    private static ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    //private static ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    //private static ECParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");

    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/CBC/PKCS5Padding";

    private static Random rand;

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        try{
            rand = SecureRandom.getInstanceStrong();
        }catch(Exception e){

        }

    }

//    /**
//     * 简陋的密钥生成方法，仅供参考
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, String> SM2key() throws Exception {
//        //由X、Y生成公钥核签
//        try {
//            SecureRandom random = new SecureRandom();
//            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            ECParameterSpec parameterSpec = new ECParameterSpec(DOMAIN_PARAMS_FROM_XY.getCurve(), DOMAIN_PARAMS_FROM_XY.getG(),
//                    DOMAIN_PARAMS_FROM_XY.getN(), DOMAIN_PARAMS_FROM_XY.getH());
//            kpg.initialize(parameterSpec, random);
//            KeyPair keyPair = kpg.generateKeyPair();
//            BCECPrivateKey vk =  (BCECPrivateKey)keyPair.getPrivate();
//            BCECPublicKey pk = (BCECPublicKey)keyPair.getPublic();
//
//            byte[] publicKey = Hex.decode("04" + Hex.encode(encodePublicKey(pk.getQ().getXCoord().toBigInteger(),pk.getQ().getYCoord().toBigInteger())));
//            byte[] privateKey = padding(vk.getD().toByteArray(),32);
//            Map<String, byte[]> map = new HashMap();
////            map.put("publickey", publicKey);
////            map.put("privatekey", privateKey);
//            Map<String, String> kp = new HashMap<>();
//            kp.put("publickey", Base64.getEncoder().encodeToString(publicKey));
//            kp.put("privatekey", Base64.getEncoder().encodeToString(privateKey));
//            return kp;
//        }catch (Exception ex)
//        {
//            System.out.println(ex.getMessage());
//            return  null;
//        }
//    }

    ///**
    // * 对接可以参考的签名算法
    // * @param privateKey Base64编码后字符串格式的私钥
    // * @param srcData 原文
    // * @return
    // * @throws SMSignException
    // */
    //public static String SM2SignWithSM3(String privateKey,String srcData) throws SMSignException
    //{
    //    if(privateKey == null || privateKey.isEmpty())
    //    {
    //        throw new SMSignException("密钥为空");
    //    }
    //    if(srcData == null || srcData.isEmpty())
    //    {
    //        throw new SMSignException("原文为空");
    //    }
    //    try {
    //        byte[] VKValue = Base64.getDecoder().decode(privateKey);
    //        String pkHex = Hex.toHexString(VKValue);
    //        BigInteger d = new BigInteger(pkHex, 16);
    //        BCECPrivateKey bcecPrivateKey = getPrivatekeyFromD(d);
    //        //待签名报文
    //        byte[] plainText = srcData.getBytes(CHARSET_ENCODING);
    //        byte[] signaturebyte = rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(plainText ,UserId.getBytes(),bcecPrivateKey));
    //        String signatureStr = Base64.getEncoder().encodeToString(signaturebyte);
    //        return signatureStr;
    //    }
    //    catch (Exception ex){
    //        throw new SMSignException("SM2WtiehSM3 Sign Error",ex);
    //    }
    //}

    ///**
    // * 对接可参考的验签方法
    // * @param srcData 原文
    // * @param signedData BASE64编码后的签名
    // * @param publicKey BASE64编码后的公钥
    // * @return
    // * @throws SMSignException
    // */
    //public static boolean verifySm3WithSm2(String srcData, String signedData, String publicKey) throws SMSignException
    //{
    //    if(srcData == null || signedData.isEmpty()) throw new SMSignException("原文为空");
    //    if(signedData == null || signedData.isEmpty()) throw new SMSignException("签名为空");
    //    if(publicKey == null || publicKey.isEmpty()) throw new SMSignException("公钥为空");
    //    try {
    //        byte[] PKValue = Base64.getDecoder().decode(publicKey);
    //        ECPublicKey publickey = encodePublicKey(PKValue);
    //        byte[] msg = srcData.getBytes(CHARSET_ENCODING);
    //        byte[] signature = Base64.getDecoder().decode(signedData);
    //        return verifySm3WithSm2Asn1Rs(msg, UserId.getBytes(), rsPlainByteArrayToAsn1(signature), publickey);
    //    }
    //    catch (Exception ex)
    //    {
    //        throw new SMSignException("SM2WtiehSM3 verifySign Error",ex);
    //    }
    //}

    /**
     * 对接方可参考的SM4加密算法 加密模式CBC/PKCS5Padding
     * @param data 原文
     * @param keyStr 密钥
     * @param ivStr  向量
     * @return
     * @throws SM4EncryptException
     */
    public static String SM4EncrptyWithCBC(String data, String keyStr, String ivStr) throws SM4EncryptException
    {
        if(data == null || data.isEmpty()) throw new SM4EncryptException("原文为空");
        if(keyStr == null || keyStr.isEmpty()) throw new SM4EncryptException("密钥为空");
        if(ivStr == null || ivStr.isEmpty()) throw new SM4EncryptException("向量为空");
        try {
            byte[] srcData = data.getBytes();
            byte[] key = Hex.decode(keyStr);
            byte[] iv = Hex.decode(ivStr);
            byte[] encryptData = sm4EncryptCBC(key, srcData, iv);
            return new String(encryptData, CHARSET_ENCODING);
        }
        catch (Exception ex)
        {
            throw new SM4EncryptException("Encrypt Error",ex);
        }
    }

    /**
     * 对接可参考的SM4解密
     * @param content 密文
     * @param keyStr 密钥
     * @param ivStr 向量
     * @return
     * @throws SM4DecryptException
     */
    public static String SM4DecryptWithCBC(String content, String keyStr, String ivStr)throws SM4DecryptException
    {
        if(content == null || content.isEmpty()) throw new SM4DecryptException("原文为空");
        if(keyStr == null || keyStr.isEmpty()) throw new SM4DecryptException("密钥为空");
        if(ivStr == null || ivStr.isEmpty()) throw new SM4DecryptException("向量为空");
        try
        {
            byte[] key = Hex.decode(keyStr);
            byte[] iv = Hex.decode(ivStr);
            byte[] ciper = Hex.decode(content);
            byte[] plain;
            plain = sm4DecryptCBC(key,ciper,iv);
            return new String(plain,CHARSET_ENCODING);
        }
        catch (Exception ex)
        {
            throw new SM4DecryptException("Encrypt Error",ex);
        }
    }

    ///**
    // * 加密方法
    // * @param src
    // * @param pubKey
    // * @return
    // * @throws SM2Exception
    // */
    //public static String sm2Encrypt(String src,String pubKey) throws SM2Exception
    //{
    //    try {
    //        if(src == null || src.isEmpty()) throw new SM2Exception("原文为空");
    //        if(pubKey == null || pubKey.isEmpty()) throw new SM2Exception("公钥为空");
    //
    //        byte[] PKValue = Base64.getDecoder().decode(pubKey);
    //        byte[] data = src.getBytes(CHARSET_ENCODING);
    //        ECPublicKey publickey = encodePublicKey(PKValue);
    //        BCECPublicKey localECPublicKey = (BCECPublicKey) publickey;
    //        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), ecDomainParameters);
    //        SM2Engine sm2Engine = new SM2Engine();
    //        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
    //
    //        byte[] ciphers =  changeC1C2C3ToC1C3C2(sm2Engine.processBlock(data, 0, data.length));
    //        return Base64.getEncoder().encodeToString(ciphers);
    //    } catch (Exception e) {
    //        throw new RuntimeException(e);
    //    }
    //}

    /**
     * SM接密
     * @param data
     * @param privateKey
     * @return
     * @throws SM2Exception
     */
    public static String sm2Decrypt(String data,String privateKey) throws SM2Exception
    {
        try
        {
            if(data == null || data.isEmpty()) throw new SM2Exception("密文为空");
            if(privateKey == null || privateKey.isEmpty()) throw new SM2Exception("私钥为空");

            byte[] VKValue = Base64.getDecoder().decode(privateKey);
            byte[] dataBytes = changeC1C3C2ToC1C2C3(Base64.getDecoder().decode(data.getBytes()));
            String pkHex = Hex.toHexString(VKValue);
            BigInteger d = new BigInteger(1, VKValue);
            ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(d, ecDomainParameters);
            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(false, ecPrivateKeyParameters);

            byte[] src = sm2Engine.processBlock(dataBytes, 0, dataBytes.length);
            return new String(src);
        }
        catch (Exception ex)
        {
            throw new SM2Exception("SM2 Decrypt Error",ex);
        }
    }

    ///**
    // * BigInterger的密钥转PriavateKey
    // */
    //public static BCECPrivateKey getPrivatekeyFromD(BigInteger d){
    //    ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
    //    return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    //}

    /**
     *
     * @param msg
     * @param userId
     * @param privateKey
     * @return rs in <b>asn1 format</b>
     */
    private static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId, PrivateKey privateKey){
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", "BC");
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            byte[] sig = signer.sign();
            return sig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer){
        ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }
    private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS){
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // r and s are the result of mod n, so they should be less than n and have length<=32
        byte[] rs = rOrS.toByteArray();
        if(rs.length == RS_LEN) return rs;
        else if(rs.length == RS_LEN + 1 && rs[0] == 0) return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        else if(rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte)0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            //throw new RuntimeException("err rs: " + org.bouncycastle.util.encoders.Hex.toHexString(rs));
            return null;
        }
    }
    /**
     *
     * @param msg
     * @param userId
     * @param rs r||s，直接拼接byte数组的rs
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey){
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs), publicKey);
    }

    /**
     *
     * @param msg
     * @param userId
     * @param rs in <b>asn1 format</b>
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey){
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", "BC");
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign){
        if(sign.length != RS_LEN * 2) throw new RuntimeException("err rs. ");
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ///**
    // * Byte[]格式公钥转Publickey
    // * @param value
    // * @return
    // * @throws SMSignException
    // */
    //private static ECPublicKey  encodePublicKey(byte[] value) throws SM2Exception{
    //    byte[] x = new byte[32];
    //    byte[] y = new byte[32];
    //    System.arraycopy(value, 1, x, 0, 32);
    //    System.arraycopy(value, 33, y, 0, 32);
    //    BigInteger X = new BigInteger(1, x);
    //    BigInteger Y = new BigInteger(1, y);
    //    ECPoint Q = spec.getCurve().createPoint(X, Y);
    //    ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
    //    ECParameterSpec spec = new ECParameterSpec(parameterSpec.getCurve(), parameterSpec.getG(), parameterSpec.getN(), parameterSpec.getH(), parameterSpec.getSeed());
    //    try {
    //        KeyFactory keyFactory = KeyFactory.getInstance("EC");
    //        ECPublicKey ecPublicKey = (ECPublicKey) keyFactory.generatePublic(new ECPublicKeySpec(Q, spec));
    //        return ecPublicKey;
    //    }
    //    catch (Exception ex)
    //    {
    //        throw new SM2Exception("error in getPublickey",ex);
    //    }
    //}

    public static byte[] encodePublicKey(BigInteger x,BigInteger y)
    {
        byte[] xBuf = padding(x.toByteArray(), 32);
        byte[] yBuf = padding(y.toByteArray(), 32);
        byte[] encoded = new byte[xBuf.length + yBuf.length];
        System.arraycopy(xBuf, 0, encoded, 0, xBuf.length);
        System.arraycopy(yBuf, 0, encoded, xBuf.length, yBuf.length);
        return encoded;
    }

    public static byte[] padding(byte[] key, int length) {
        if (key.length == length) {
            return key;
        } else {
            byte[] dest;
            if (key.length > length) {
                dest = new byte[length];
                System.arraycopy(key, key.length - length, dest, 0, length);
                return dest;
            } else {
                dest = new byte[length];

                for(int i = 0; i < length - key.length; ++i) {
                    dest[i] = 0;
                }

                System.arraycopy(key, 0, dest, length - key.length, key.length);
                return dest;
            }
        }
    }

    /**
     * SM4/CBC/PKCS5Padding
     * @param keyBytes
     * @param plain
     * @param iv
     * @return
     */
    public static byte[] sm4EncryptCBC(byte[] keyBytes, byte[] plain, byte[] iv){
        if(keyBytes.length != 16) throw new RuntimeException("err key length");

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            Key sm4Key = new SecretKeySpec(keyBytes, "SM4");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, sm4Key, ivParameterSpec);
            return cipher.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4DecryptCBC(byte[] keyBytes, byte[] cipher,byte[] iv){
        if(keyBytes.length != 16) throw new RuntimeException("err key length");

        try {
            Cipher in = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            Key key = new SecretKeySpec(keyBytes, "SM4");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            in.init(Cipher.DECRYPT_MODE, key,ivParameterSpec);
            return in.doFinal(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * bc加解密使用旧标c1||c3||c2，此方法在解密前调用，将密文转化为c1||c2||c3再去解密
     * @param c1c3c2
     * @return
     */
    private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;
        final int c3Len = 32;
        byte[] result = new byte[c1c3c2.length];
        System.arraycopy(c1c3c2, 0, result, 0, c1Len);
        System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length - c1Len - c3Len);
        System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len);
        return result;
    }


    /**
     * bc加解密使用旧标c1||c2||c3，此方法在加密后调用，将结果转化为c1||c3||c2
     * @param c1c2c3
     * @return
     */
    private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;
        final int c3Len = 32;
        byte[] result = new byte[c1c2c3.length];
        System.arraycopy(c1c2c3, 0, result, 0, c1Len);
        System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len);
        System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length - c1Len - c3Len);
        return result;
    }

    /**
     * SM4向量生成算法
     * @return
     */
    public static String SM4ProductIV() {

        return RandomStringUtils.randomNumeric(32);
    }

    /**
     *SM4 对称秘钥生成算法
     * @return
     */
    public static String SM4ProductKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
