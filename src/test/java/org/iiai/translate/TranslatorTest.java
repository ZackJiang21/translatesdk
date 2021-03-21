package org.iiai.translate;

import org.junit.Test;

public class TranslatorTest {
    @Test
    public void testTranslator() {
//        String url = "https://7e6dc992fc7d4cd38f8b840c67c744a8.apigw.ap-southeast-3.huaweicloud.com/v1/infers/dfd26b24-c879-4d27-8317-49b2b9982d47/translator/translate";
        String token = null;
        String url = "http://10.111.137.201:3389/translator/translate";
        String test = "السيد فاضل المزروعي                   المحترم\n" +
                "\n" +
                " \n" +
                "\n" +
                "تحية طيبة وبعد،،،\n" +
                "\n" +
                "       \n" +
                "\n" +
                "الموضوع: نطاق العمل إدارة الخدمة\n" +
                "\n" +
                " \n" +
                "\n" +
                "مرفق لسيادتكم نطاق العمل المعتمد للموضوع اعلاه بناء على الاجتماع الاخير معكم ومع ممثلي مركز البيانات الوطني.\n" +
                "\n" +
                " \n" +
                "\n" +
                "المرفقات:\n" +
                "\n" +
                "نطاق عمل إدارة الخدمة لمدة 3 سنوات (Manage Service)";
        test = test.replace(' ', '\u00a0');
        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            System.out.println(test.length());
            String result = Translator.getTranslation("ar2en", test, url, token);
            System.out.println(result);
        }
    }
}
