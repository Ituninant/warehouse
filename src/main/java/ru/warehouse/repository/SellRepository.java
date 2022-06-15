package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.warehouse.api.dto.AbcAnalysisDto;
import ru.warehouse.model.Sell;

import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Integer> {

    @Query(nativeQuery = true,
           value = "SELECT\n" +
                   "    *,\n" +
                   "    CASE\n" +
                   "        WHEN total <= 80 THEN 'A'\n" +
                   "        WHEN total > 80 AND total <= 95 THEN 'B'\n" +
                   "        ELSE 'C'\n" +
                   "        END rank\n" +
                   "FROM\n" +
                   "    (SELECT\n" +
                   "        *,\n" +
                   "        SUM(contribution) OVER (ORDER BY contribution DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS total\n" +
                   "    FROM (SELECT i_s.id id,\n" +
                   "                 i_s.name name,\n" +
                   "                 SUM(i_s.profit) profit,\n" +
                   "                 ROUND(SUM(i_s.profit) / total_profit * 100, 2) contribution\n" +
                   "          FROM (\n" +
                   "                    SELECT p.id,\n" +
                   "                        si.count count,\n" +
                   "                        p.name,\n" +
                   "                        s.sell_type,\n" +
                   "                        si.count *\n" +
                   "                            CASE\n" +
                   "                                WHEN s.sell_type = 'RETAIL' THEN p.sell_price\n" +
                   "                                WHEN s.sell_type = 'WHOLESALE' THEN p.wholesale_price\n" +
                   "                                ELSE 0 END - p.purchase_price\n" +
                   "                            profit\n" +
                   "                    FROM sell s\n" +
                   "                        JOIN sell_item si ON si.sell_id = s.id\n" +
                   "                        JOIN product p ON si.product_id = p.id\n" +
                   "               ) i_s\n" +
                   "               CROSS JOIN (\n" +
                   "                  SELECT\n" +
                   "                       SUM(profit) total_profit\n" +
                   "                  FROM (SELECT si.count *\n" +
                   "                                   CASE\n" +
                   "                                       WHEN s.sell_type = 'RETAIL' THEN p.sell_price\n" +
                   "                                       WHEN s.sell_type = 'WHOLESALE' THEN p.wholesale_price\n" +
                   "                                       ELSE 0 END - p.purchase_price\n" +
                   "                                   profit\n" +
                   "                        FROM sell s\n" +
                   "                                 JOIN sell_item si ON si.sell_id = s.id\n" +
                   "                                 JOIN product p ON si.product_id = p.id\n" +
                   "                  ) i_s) t_p\n" +
                   "          GROUP BY id,\n" +
                   "                   name,\n" +
                   "                   total_profit\n" +
                   "          ORDER BY contribution DESC\n" +
                   "         ) i_s\n" +
                   "    ORDER BY contribution DESC) i_s")
    List<AbcAnalysisDto> getAbcAnalysis();

}