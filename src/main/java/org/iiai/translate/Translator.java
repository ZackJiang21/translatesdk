package org.iiai.translate;

import org.iiai.translate.constant.ErrorCode;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.exception.TranslatorException;
import org.iiai.translate.model.Document;
import org.iiai.translate.util.TranslateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Translator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Translator.class);

    public static String getTranslation(String modeId, String input, String url) {
        if (!TranslateConst.MODEL_LIST.contains(modeId)) {
            LOGGER.error("Model id {} not illegal", modeId);
            throw new TranslatorException(ErrorCode.PARAM_ILLEGAL);
        }
        if (input.length() >= TranslateConst.INPUT_LIMIT || input.length() == 0) {
            LOGGER.error("Input length not illegal: {}", input.length());
            throw new TranslatorException(ErrorCode.PARAM_ILLEGAL);
        }
        Document document = new Document(input);
        String translation = TranslateUtil.getTranslation(document, modeId, url);
        return translation;
    }

    public static void main(String[] args) {
        String test = "أحدث انتشار فيروس \"كورونا\" هزة عنيفة في أسواق المال والأسهم العالمية، حيث أدى ذلك لحركة كبيرة في عمليات البيع والشراء لأسهم كبرى الشركات تخوفا من هبوط حاد يرافق ذلك.\n" +
                "\n" +
                "وكان انتشار عدد من الأمراض الفيروسية في وقت سابق قد تسبب بهبوط كبرى أسواق الأسهم العالمية، فبسبب فيروس \"السارس\" في الصين عام 2003 و\"أنفلونزا الخنازير\" في المكسيك عام 2009، و\"إيبولا\" في أفريقيا في عام 2014، و\"زيكا\" البرازيل في 2016، هبطت أسعار الأسهم العالمية في المتوسط بنسبة وصلت لـ7,4 في المئة.\n" +
                "\n" +
                "لتبدأ بعدها مرحلة انتعاش، بحسب ما صرح ميخائيل أريستاكيسيان، رئيس قسم معلومات السوق العالمية في مجموعة Finam، لتبدأ عملية الصعود بنسبة وصلت لـ23,1 في المئة.\n" +
                "\n" +
                "وخلال انتشار \"سارس\" في الصين في عام 2003 انخفض مؤشر MSCI China بنسبة 8,6 في المئة، لينمو  لاحقا بنسبة 14,7 في الشهر ليصل إلى نسبة نمو بلغت 30,9 في ثلاثة أشهر.\n" +
                "وأضاف أريستاكيسيان بأن كبرى أسواق الأسهم تعرضت لهزات عنيفة نتيجة لتفشي \"كورونا\" في الصين في الوقت الذي كانت فيه الصين تحتفل بعامها الجديد، لتفتح أسواقها المالية في 3 فبراير/ شباط.\n" +
                "\n" +
                "افتتحت البورصة الصينية على وضعية أصبحت أكثر وضوحا بعد تفشي \"كورونا\".\n" +
                "\n" +
                "الصينيون استغلوا \"كورونا\" اقتصاديا\n" +
                "تحدث خبير المعهد الروسي للدراسات الاستراتيجية، الحائز على دكتوراه في الاقتصاد، ميخائيل بيليف، عن السوق المالي وكيف تمكن الصينيون من توظيف هذا الوباء اقتصاديا.\n" +
                "\n" +
                "قائلا \" لايمكننا القول بأن المستثمرين هم وراء حدوث هذا الوباء إلا أنهم قاموا باستثماره بشكل مثالي، وخاصة المستثمرون الصينيون الذين قاموا بشراء أسهم العديد من الشركات وبأسعار مرضية لهم. وأدرك الصينيون بأن خطر هذا الفيروس لاحقا سيتراجع، الأمر الذي سينعكس على سعر الأسهم ليعود إلى وضعه المستقر، لتصبح أسهم الشركات بأيدهم وليس بأيدي الشركاء الأجانب، ليتمكنوا لاحقا من فرض كلمتهم في العمليات التجارية لهذه الشركات، كونهم يملكون الحصص الأكبر\".\n" +
                "وأضاف بيليف \"قامت الصين باستغلال أزمة انخفاض أسعار النفط لتقوم بشرائه بكميات كبيرة وبسعر منخفض، للاستهلاك المحلي ولأغراض استراتيجية، على أمل أن يتعافى سعر النفط لتقوم ببيعه لاحقا\".";
        Translator.getTranslation("ar2en", test, "http://10.111.137.125:5010/translator/translate");
    }
}
