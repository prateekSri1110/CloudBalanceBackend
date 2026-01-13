package com.CloudKeeper.CloudBalanceBackend.service;

import com.CloudKeeper.CloudBalanceBackend.modal.CEdataDTO;
import com.snowflake.snowpark.Row;
import com.snowflake.snowpark.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CEService {
    private final Session session;

    public List<CEdataDTO> ceGroupByFilter(String type, String start, String end) {
        String query = String.format("""
                        SELECT %s,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '01' THEN cost ELSE 0 END) AS JAN,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '02' THEN cost ELSE 0 END) AS FEB,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '03' THEN cost ELSE 0 END) AS MAR,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '04' THEN cost ELSE 0 END) AS APR,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '05' THEN cost ELSE 0 END) AS MAY,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '06' THEN cost ELSE 0 END) AS JUN,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '07' THEN cost ELSE 0 END) AS JUL,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '08' THEN cost ELSE 0 END) AS AUG,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '09' THEN cost ELSE 0 END) AS SEP,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '10' THEN cost ELSE 0 END) AS OCT,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '11' THEN cost ELSE 0 END) AS NOV,
                            SUM(CASE WHEN TO_VARCHAR(bill_date, 'MM') = '12' THEN cost ELSE 0 END) AS DEC,
                            SUM(cost) AS total
                        FROM cloudbalance.public.costreport
                        WHERE bill_date >= TO_DATE('%s-01')
                          AND bill_date <  DATEADD(month, 1, TO_DATE('%s-01'))
                        GROUP BY %s, TO_VARCHAR(bill_date, 'YYYY')
                        ORDER BY total DESC;
                        """,
                type, start, end, type);

        Row[] rows = session.sql(query).collect();
        List<CEdataDTO> costData = new ArrayList<>();

        for (Row row : rows) {
            Map<String, Long> monthlyCost = new LinkedHashMap<>();
            monthlyCost.put("JAN", row.getLong(1));
            monthlyCost.put("FEB", row.getLong(2));
            monthlyCost.put("MAR", row.getLong(3));
            monthlyCost.put("APR", row.getLong(4));
            monthlyCost.put("MAY", row.getLong(5));
            monthlyCost.put("JUN", row.getLong(6));
            monthlyCost.put("JUL", row.getLong(7));
            monthlyCost.put("AUG", row.getLong(8));
            monthlyCost.put("SEP", row.getLong(9));
            monthlyCost.put("OCT", row.getLong(10));
            monthlyCost.put("NOV", row.getLong(11));
            monthlyCost.put("DEC", row.getLong(12));

            costData.add(new CEdataDTO(type, row.getString(0), monthlyCost, row.getLong(13)));
        }
        return costData;
    }
}
