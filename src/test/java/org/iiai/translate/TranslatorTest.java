package org.iiai.translate;

import org.junit.Test;

public class TranslatorTest {
    @Test
    public void testTranslator() {
//        String url = "https://7e6dc992fc7d4cd38f8b840c67c744a8.apigw.ap-southeast-3.huaweicloud.com/v1/infers/dfd26b24-c879-4d27-8317-49b2b9982d47/translator/translate";
//        String token = "MIIYRwYJKoZIhvcNAQcCoIIYODCCGDQCAQExDTALBglghkgBZQMEAgEwghZZBgkqhkiG9w0BBwGgghZKBIIWRnsidG9rZW4iOnsiZXhwaXJlc19hdCI6IjIwMjAtMDQtMDFUMTU6MDU6MDMuNjA0MDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwiY2F0YWxvZyI6W10sInJvbGVzIjpbeyJuYW1lIjoidGVfYWRtaW4iLCJpZCI6IjAifSx7Im5hbWUiOiJ0ZV9hZ2VuY3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9laXBfaXB2NiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3YyeCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19zcG90X2luc3RhbmNlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaXZhc192Y3JfdmNhIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWVmX25vZGVncm91cCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NjaV9tb3VudG9ic3Bvc2l4IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2FzY2VuZF9rYWkxIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZGJzX3JpIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYm1zX2hwY19oMmxhcmdlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX2Vzc2QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pb2RwcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2JhdGNoX2Vjc19jbHVzdGVyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV92MTAwIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY2JzX3FpIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZHdzX3BvYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX21lZXRpbmdfZW5kcG9pbnRfYnV5IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX29mZmxpbmVfaXIzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2lzX3Nhc3JfZW4iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9WSVNfSW50bCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19ncHVfcDJzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX3ZvbHVtZV9yZWN5Y2xlX2JpbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZjYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZjcCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RwcCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29jc21hcnRjYW1wdXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ia3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tZWV0aW5nX2hhcmRhY2NvdW50X2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX211bHRpX2JpbmQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ubHBfbXQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9laXBfcG9vbCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2llZl9mdW5jdGlvbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfYXAtc291dGhlYXN0LTNkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcHJvamVjdF9kZWwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tNm10IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX3JldHlwZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FhZF9mcmVlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWxiX2d1YXJhbnRlZWQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2NuLXNvdXRod2VzdC0yYiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Nmc3R1cmJvIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaHZfdmVuZG9yIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tbm9ydGgtNGQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9JRUMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF90YXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zaXNfYXNzZXNzX211bHRpbW9kZWwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2VfbWNwX3RoYWkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ubHBfbGdfdGciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zZXJ2aWNlc3RhZ2VfbWdyX2R0bSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tbm9ydGgtNGYiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jcGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9Nb2RlbEFydHNBc2NlbmQ5MTAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tZWV0aW5nX2hpc3RvcnlfY3VzdG9tX2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3dzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2dwdV9nNXIiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lbGJfbWlncmF0ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NjaV9rdW5wZW5nIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcmlfZHdzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaW90ZWRnZV9jYW1wdXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF91bnZlcmlmaWVkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfdmd2YXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF92cGNfZmxvd19sb2ciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9vcF9nYXRlZF9pY3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hYWRfYmV0YV9pZGMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2JzX3JlcF9hY2NlbGVyYXRpb24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pZWZfZWRnZW1lc2giLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9haXNfYXBpX2ltYWdlX2FudGlfYWQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kc3NfbW9udGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2ciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfYzZ4IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2lzX2Fzc2Vzc19hdWRpbyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3VmcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RlY19tb250aF91c2VyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfdmlwX2JhbmR3aWR0aCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19vbGRfcmVvdXJjZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19raTEiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kY3NfcmkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2VfYnVyc3RfdG9fY2NpIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfdmdpdnMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9vYnNfZHVhbHN0YWNrIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWRjbSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NzYnNfcmVzdG9yZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2l2c2NzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfSVdCUHVibGljVGVzdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lwdjZfZHVhbHN0YWNrIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfdnBuX3ZndyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lydGMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2VfYm1zMiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3BjYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Znd3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2VfYXNtX2hrIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY3Nic19wcm9ncmVzc2JhciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lvdi10cmlhbCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Jkc19hcm0iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfYzZ4X3ZpcnRpb19uZXQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ldnNfcG9vbF9jYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Rkc19hcm0iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3Nfb2ZmbGluZV9kaXNrXzQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9icyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2dzc19mcmVlX3RyaWFsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbWVldGluZ19jbG91ZF9idXkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lcHMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2JzX3Jlc3RvcmVfYWxsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbDJjZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX1dlTGlua19lbmRwb2ludF9idXkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9xdWlja2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Zjc19wYXkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pb3RhbmFseXRpY3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tYXhodWJfZW5kcG9pbnRfYnV5IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9hcC1zb3V0aGVhc3QtMWUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2FwLXNvdXRoZWFzdC0xZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX25scF9rZyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfYXAtc291dGhlYXN0LTFmIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWVmX2RldmljZV9kaXJlY3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kY3NfZGNzMl9wcm94eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc192Z3B1X2c1IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY3NfYXJtX3BvYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19yaSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tc291dGgtMWYiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2FwLXNvdXRoZWFzdC0xYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfcnUtbm9ydGh3ZXN0LTJjIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfdWxiX21paXRfdGVzdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2llZl9wbGF0aW51bSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX1ZpZGVvX0NhbXB1cyIsImlkIjoiMCJ9XSwicHJvamVjdCI6eyJkb21haW4iOnsieGRvbWFpbl90eXBlIjoiSFdDX0hLIiwibmFtZSI6Im5scF9hZXRlc3QiLCJpZCI6IjA3NzBkNzZjZDQwMDI2NWYwZmNkYzAxZjAwZjVjZjIwIiwieGRvbWFpbl9pZCI6ImM0MGMxODExMzE2ZjRjZDU5ZTA0MWQ5OGViMzg1ZjgwIn0sIm5hbWUiOiJhcC1zb3V0aGVhc3QtMyIsImlkIjoiMDc3MGVlODAyMzgwMjY2NDJmMDhjMDFmZTliYjc4N2YifSwiaXNzdWVkX2F0IjoiMjAyMC0wMy0zMVQxNTowNTowMy42MDQwMDBaIiwidXNlciI6eyJkb21haW4iOnsieGRvbWFpbl90eXBlIjoiSFdDX0hLIiwibmFtZSI6Im5scF9hZXRlc3QiLCJpZCI6IjA3NzBkNzZjZDQwMDI2NWYwZmNkYzAxZjAwZjVjZjIwIiwieGRvbWFpbl9pZCI6ImM0MGMxODExMzE2ZjRjZDU5ZTA0MWQ5OGViMzg1ZjgwIn0sIm5hbWUiOiJod2xvbmdjaHVuIiwicGFzc3dvcmRfZXhwaXJlc19hdCI6IiIsImlkIjoiMDc3MGY4N2U4MDgwMjY2OTFmZWNjMDFmNGUxYzczNTIifX19MYIBwTCCAb0CAQEwgZcwgYkxCzAJBgNVBAYTAkNOMRIwEAYDVQQIDAlHdWFuZ0RvbmcxETAPBgNVBAcMCFNoZW5aaGVuMS4wLAYDVQQKDCVIdWF3ZWkgU29mdHdhcmUgVGVjaG5vbG9naWVzIENvLiwgTHRkMQ4wDAYDVQQLDAVDbG91ZDETMBEGA1UEAwwKY2EuaWFtLnBraQIJANyzK10QYWoQMAsGCWCGSAFlAwQCATANBgkqhkiG9w0BAQEFAASCAQA5DCesvMfVnQM3dgn5bYeM3DnCUH0-g3cuUyJFxd1C3gddBl+H945s-W8eKKMleKLgBDDhelGXAjjik4fJVWaqQvCPZoz-BaBdolK+TAec6zMlfF9p7qlHZpBNibECi6zcqbwLW5SIsTkQ5-To29euCJTIS9NCpnzjoz2B2l1qLbb0IKjZfTFilJOhFEsgvB6+U2rsLnV3Fw24F0aEtHdBnkShdxDAeCW9Wfz1xf3KfY6DX3oJAFf2cTMrnBWsy3tEHjQEHQ6HWWFmWg+sFdRMX20hCIhIWhipsdBAf4XGjrSHQMfraNbgCiBaSI6OTEjIf8jxcjXvLnqvm66HsybL";
        String url = "http://159.138.108.169/translator/translate";
        String token = "12";
//        String test = "بعض المرضى تعافوا من فيروس كوفيد- 19 وكانت كل نتائج الاختبارات التي أجريت لهم سلبية، ولكن في وقت لاحق تحولت نتيجة الاختبار إلى إيجابية. إن عدوى فيروس كورونا تشبه حالات البرد الشائعة والتي تؤدي عادة إلى خلق المناعة لدى المريض، فما الذي يختلف مع هذا الفيروس؟";
       String test = "Whereas, with regard to technical requirements, it is appropriate to utilize basically those adopted by the United Nations Economic Commission for Europe in its Regulation No 21 (\"Uniform requirements concerning the approval of vehicles with regard to their interior fittings\") which is annexed to the Agreement of 20 March 1958 concerning the adoption of uniform conditions of approval and reciprocal recognition of approval for motor vehicle equipment and parts,";
//        String test = "How are you?";
        for (int i = 0; i < 1; i++) {
            String result = Translator.getTranslation("en2ar", test, url, token);
            System.out.println(result);
        }
    }
}
