package com.sbf.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;

@RequestMapping("/test/{input}")
@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String test(@PathVariable String input) {
        final char[] chars = input.toCharArray();
        final StringBuilder result = new StringBuilder();

        result.append("Наибольшая подстрока из неповторяющихся символов подряд: ").append(getMaxNotRepeatedSub(chars)).append("; ");

        boolean isPolyndrom = true;

        final int n = chars.length;
        for(int i1 = n / 2 - 1, i2 = n - i1 - 1; i1 >= 0; i1--, i2++){
            final char c1 = chars[i1];
            final char c2 = chars[i2];

            if(c1 != c2){
                isPolyndrom = false;
                chars[i2] = c1;
                chars[i1] = c2;
            }
        }

        result.append("Полиндром: ").append(isPolyndrom).append("; ").append("Реверс строки: ").append(chars);

        return result.toString();
    }

    /**
     * Поиск наибольшей подстроки из неповторяющихся символов подряд
     * @param chars массив символов
     * @return наибольшая подстрока из неповторяющихся символов
     */
    private String getMaxNotRepeatedSub(char[] chars)
    {
        final int n = chars.length;
        int iStart = 0;//Начало искомой подстроки
        int maxLength = 1; //Длинна подстроки

        int i0 = 0;//Начало текущей подстроки
        int i1 = 0;//Окончание текущей подстроки
        while(++i1 < n){
            final char c = chars[i1];//Тукущий символ
            for(int i = i0; i < i1; i++){
                //Если текущий символ совпал с одним из символов подстроки
                if(c == chars[i]){
                    final int curentLength = i1 - i0;//Длинна найденной подстроки
                    if(maxLength < curentLength){
                        maxLength = curentLength;
                        iStart = i0;
                    }
                    i0 = i + 1;//Начинаем новую подстроку с символа слудующего за первым вхождением текущего символа
                    break;
                }
            }
        }

        final int curentLength = i1 - i0;
        if(maxLength < curentLength){
            maxLength = curentLength;
            iStart = i0;
        }

        return new String(chars, iStart, maxLength);
    }
}
