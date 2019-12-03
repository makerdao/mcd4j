# mcd4j

Java library for interacting with MCD

### Prerequisite:
- java
- maven

### Build:
    mvn clean
    mvn package -Dmaven.test.skip=true

For running tests on kovan:  
    `mvn -DprivateKey=71F1EA.. -DkovanEndpoint=https://localhost:8545/ test`

For running tests on mainnet:  
    `mvn -DprivateKey=71F1EA.. -DmainnetEndpoint=https://localhost:8545/ test`
    

### Usage:

```java
String registeredEthAccount = "0xD71..........";

// Instantiate Mcd object
Mcd mcd = new Mcd(web3j, credentials, gasProvider);

// get services
SavingsService savingsService = mcd.getSavingsService();
DSProxyService dsProxyService = mcd.getDSProxyService();
AllowanceService allowanceService = mcd.getAllowanceService();

// get total DAI in DSR
BigDecimal totalDai = savingsService.getTotalDai();

// 1 - get ds proxy for registered ETH account, if no proxy then create one.
// Subsequent calls to DSProxyService.getProxy return same instance of DSProxy object
DSProxy dsProxy = dsProxyService.getProxy(registeredEthAccount, true);

// 2 - set proxy allowance of 10 DAI
allowanceService.requireAllowance(registeredEthAccount,
                    dsProxy.getContractAddress(),
                    TokenSymbols.DAI,
                    BigDecimal.TEN);

// 3 - join 3 DAI
TransactionReceipt receiptJoinThreeDai = savingsService.join(dsProxy, BigDecimal.valueOf(3));
// Do something with TransactionReceipt
// check proxy DSR balance
BigDecimal balance = savingsService.getBalanceOf(dsProxy.getContractAddress());

// 4 - exit 1 DAI
TransactionReceipt receiptExitOneDai = savingsService.exit(dsProxy, BigDecimal.ONE);
// Do something with TransactionReceipt

// 5 - exit all DAI
TransactionReceipt receiptExitAllDai = savingsService.exitAll(dsProxy);
// Do something with TransactionReceipt

// 6 - remove proxy actions allowance
allowanceService.removeAllowance(registeredEthAccount,
                     dsProxy.getContractAddress(),
                     TokenSymbols.DAI);
```

See McdTest.java for sample

