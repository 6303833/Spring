package com.example.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.DTOs.CategoryAllAmountsDTO;
import com.example.DTOs.CategoryAmountDTO;
import com.example.DTOs.MonthlyTrendDTO;
import com.example.entity.Transactions;

public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
//	  
//	  // Filtering support
//	  List<Transactions> findByCategoryAndDateBetweenAndAmountBetween(
//	      String category, LocalDate start, LocalDate end, Double min, Double max);
	@Query("SELECT SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), " +
		       "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) " +
		       "FROM Transactions t")
		List<Object[]> getTotalIncomeAndExpense();
	
	
	// Sum by category
	@Query("SELECT new com.example.DTOs.CategoryAmountDTO(t.category, SUM(t.amount)) " + "FROM Transactions t "
			+ "WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month AND t.type=:type " + "GROUP BY t.category ")
	List<CategoryAmountDTO> getCategoryAmount(@Param("year") int year, @Param("month") int month,
			@Param("type") String type);

	@Query("SELECT new com.example.DTOs.CategoryAllAmountsDTO( " +
		       "t.category, " +
		       "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END), " +
		       "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), " +
		       "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE -t.amount END)) " +
		       "FROM Transactions t " +
		       "WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month " +
		       "GROUP BY t.category")
	List<CategoryAllAmountsDTO> getCategoryAllAmounts(@Param("year") int year, @Param("month") int month);
	
	@Query("SELECT new com.example.DTOs.MonthlyTrendDTO(MONTH(t.date), SUM(t.amount)) " +
		       "FROM Transactions t " +
		       "WHERE YEAR(t.date) = :year AND t.type = :type " +
		       "GROUP BY MONTH(t.date) " +
		       "ORDER BY MONTH(t.date)")
		List<MonthlyTrendDTO> getMonthlyAmountByYearAndType(@Param("year") int year,
		                                                      @Param("type") String type);
	
	


}
