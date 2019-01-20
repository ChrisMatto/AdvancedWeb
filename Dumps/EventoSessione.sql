CREATE EVENT deleteTokens
	ON SCHEDULE 
		EVERY 10 MINUTE
    DO
		DELETE FROM sessione WHERE Data < (NOW() - INTERVAL 1 HOUR);