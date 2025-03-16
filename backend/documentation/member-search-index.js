memberSearchIndex = [{"p":"com.clientwallet","c":"ClientWalletApplication","l":"ClientWalletApplication()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.model","c":"TransactionStatus","l":"COMPLETED"},{"p":"com.clientwallet.controller","c":"WalletController","l":"create(WalletCreateRequest)","u":"create(com.clientwallet.dto.wallet.WalletCreateRequest)"},{"p":"com.clientwallet.service","c":"WalletService","l":"create(WalletCreateRequest)","u":"create(com.clientwallet.dto.wallet.WalletCreateRequest)"},{"p":"com.clientwallet.validation","c":"CurrencyValidator","l":"CurrencyValidator()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.model","c":"Currency","l":"CZK"},{"p":"com.clientwallet.model","c":"TransactionType","l":"DEPOSIT"},{"p":"com.clientwallet.controller","c":"WalletController","l":"deposit(WalletDepositRequest)","u":"deposit(com.clientwallet.dto.wallet.WalletDepositRequest)"},{"p":"com.clientwallet.service","c":"WalletService","l":"deposit(WalletDepositRequest)","u":"deposit(com.clientwallet.dto.wallet.WalletDepositRequest)"},{"p":"com.clientwallet.model","c":"Currency","l":"EUR"},{"p":"com.clientwallet.repository","c":"WalletRepository","l":"existsByName(String)","u":"existsByName(java.lang.String)"},{"p":"com.clientwallet.model","c":"TransactionStatus","l":"FAILED"},{"p":"com.clientwallet.repository","c":"TransactionRepository","l":"findBySenderWalletIdOrReceiverWalletId(UUID, UUID)","u":"findBySenderWalletIdOrReceiverWalletId(java.util.UUID,java.util.UUID)"},{"p":"com.clientwallet.controller","c":"WalletController","l":"get(UUID)","u":"get(java.util.UUID)"},{"p":"com.clientwallet.service","c":"WalletService","l":"get(UUID)","u":"get(java.util.UUID)"},{"p":"com.clientwallet.service","c":"WalletService","l":"getTransactionHistory(UUID)","u":"getTransactionHistory(java.util.UUID)"},{"p":"com.clientwallet.controller","c":"WalletController","l":"getWalletHistory(UUID)","u":"getWalletHistory(java.util.UUID)"},{"p":"com.clientwallet.exception","c":"GlobalExceptionHandler","l":"GlobalExceptionHandler()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.validation","c":"ValidCurrency","l":"groups()"},{"p":"com.clientwallet.validation","c":"ValidWalletId","l":"groups()"},{"p":"com.clientwallet.exception","c":"GlobalExceptionHandler","l":"handleValidationExceptions(MethodArgumentNotValidException)","u":"handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException)"},{"p":"com.clientwallet.validation","c":"CurrencyValidator","l":"isValid(Currency, ConstraintValidatorContext)","u":"isValid(com.clientwallet.model.Currency,jakarta.validation.ConstraintValidatorContext)"},{"p":"com.clientwallet.validation","c":"WalletIdValidator","l":"isValid(UUID, ConstraintValidatorContext)","u":"isValid(java.util.UUID,jakarta.validation.ConstraintValidatorContext)"},{"p":"com.clientwallet","c":"ClientWalletApplication","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"com.clientwallet.validation","c":"ValidCurrency","l":"message()"},{"p":"com.clientwallet.validation","c":"ValidWalletId","l":"message()"},{"p":"com.clientwallet.validation","c":"ValidCurrency","l":"payload()"},{"p":"com.clientwallet.validation","c":"ValidWalletId","l":"payload()"},{"p":"com.clientwallet.model","c":"TransactionStatus","l":"PENDING"},{"p":"com.clientwallet.model","c":"Transaction","l":"prePersist()"},{"p":"com.clientwallet.model","c":"Wallet","l":"prePersist()"},{"p":"com.clientwallet.model","c":"Transaction","l":"preUpdate()"},{"p":"com.clientwallet.model","c":"Wallet","l":"preUpdate()"},{"p":"com.clientwallet.model","c":"Transaction","l":"Transaction()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.model","c":"TransactionType","l":"TRANSFER"},{"p":"com.clientwallet.controller","c":"WalletController","l":"transfer(WalletTransferRequest)","u":"transfer(com.clientwallet.dto.wallet.WalletTransferRequest)"},{"p":"com.clientwallet.service","c":"WalletService","l":"transfer(WalletTransferRequest)","u":"transfer(com.clientwallet.dto.wallet.WalletTransferRequest)"},{"p":"com.clientwallet.model","c":"Currency","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"com.clientwallet.model","c":"TransactionStatus","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"com.clientwallet.model","c":"TransactionType","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"com.clientwallet.model","c":"Currency","l":"values()"},{"p":"com.clientwallet.model","c":"TransactionStatus","l":"values()"},{"p":"com.clientwallet.model","c":"TransactionType","l":"values()"},{"p":"com.clientwallet.model","c":"Wallet","l":"Wallet()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.controller","c":"WalletController","l":"WalletController()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.dto.wallet","c":"WalletCreateRequest","l":"WalletCreateRequest()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.exception.wallet","c":"WalletCreateWalletWithNameAlreadyExistsException","l":"WalletCreateWalletWithNameAlreadyExistsException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.clientwallet.exception.wallet","c":"WalletDepositCurrencyMissMatchException","l":"WalletDepositCurrencyMissMatchException(Currency, Currency)","u":"%3Cinit%3E(com.clientwallet.model.Currency,com.clientwallet.model.Currency)"},{"p":"com.clientwallet.exception.wallet","c":"WalletDepositFailedException","l":"WalletDepositFailedException(UUID, Exception)","u":"%3Cinit%3E(java.util.UUID,java.lang.Exception)"},{"p":"com.clientwallet.dto.wallet","c":"WalletDepositRequest","l":"WalletDepositRequest()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.validation","c":"WalletIdValidator","l":"WalletIdValidator()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.exception.wallet","c":"WalletNotFoundException","l":"WalletNotFoundException(UUID)","u":"%3Cinit%3E(java.util.UUID)"},{"p":"com.clientwallet.service","c":"WalletService","l":"WalletService()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.exception.wallet","c":"WalletTransferCurrencyMissMatchException","l":"WalletTransferCurrencyMissMatchException(Currency, Currency)","u":"%3Cinit%3E(com.clientwallet.model.Currency,com.clientwallet.model.Currency)"},{"p":"com.clientwallet.exception.wallet","c":"WalletTransferFailedException","l":"WalletTransferFailedException(UUID, UUID, Exception)","u":"%3Cinit%3E(java.util.UUID,java.util.UUID,java.lang.Exception)"},{"p":"com.clientwallet.exception.wallet","c":"WalletTransferNotEnoughFundsException","l":"WalletTransferNotEnoughFundsException(BigDecimal, BigDecimal)","u":"%3Cinit%3E(java.math.BigDecimal,java.math.BigDecimal)"},{"p":"com.clientwallet.dto.wallet","c":"WalletTransferRequest","l":"WalletTransferRequest()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.exception.wallet","c":"WalletTransferSameWalletException","l":"WalletTransferSameWalletException(UUID)","u":"%3Cinit%3E(java.util.UUID)"},{"p":"com.clientwallet.exception.wallet","c":"WalletWithdrawCurrencyMissMatchException","l":"WalletWithdrawCurrencyMissMatchException(Currency, Currency)","u":"%3Cinit%3E(com.clientwallet.model.Currency,com.clientwallet.model.Currency)"},{"p":"com.clientwallet.exception.wallet","c":"WalletWithdrawFailedException","l":"WalletWithdrawFailedException(UUID, Exception)","u":"%3Cinit%3E(java.util.UUID,java.lang.Exception)"},{"p":"com.clientwallet.exception.wallet","c":"WalletWithdrawNotEnoughFundsException","l":"WalletWithdrawNotEnoughFundsException(BigDecimal, BigDecimal)","u":"%3Cinit%3E(java.math.BigDecimal,java.math.BigDecimal)"},{"p":"com.clientwallet.dto.wallet","c":"WalletWithdrawRequest","l":"WalletWithdrawRequest()","u":"%3Cinit%3E()"},{"p":"com.clientwallet.controller","c":"WalletController","l":"withdraw(WalletWithdrawRequest)","u":"withdraw(com.clientwallet.dto.wallet.WalletWithdrawRequest)"},{"p":"com.clientwallet.service","c":"WalletService","l":"withdraw(WalletWithdrawRequest)","u":"withdraw(com.clientwallet.dto.wallet.WalletWithdrawRequest)"},{"p":"com.clientwallet.model","c":"TransactionType","l":"WITHDRAWAL"}];updateSearchResults();