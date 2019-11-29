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
// Instantiate Mcd object
Mcd mcd = new Mcd(web3j, credentials, gasProvider);

// get services
SavingsService savingsService = mcd.getSavingsService();
DSProxyService dsProxyService = mcd.getDSProxyService();
AllowanceService allowanceService = mcd.getAllowanceService();

// get total DAI
BigDecimal totalDai = savingsService.getTotalDai();

// join DAI
// 1 - get ds proxy address from registered account, if no proxy then create one
String dsProxyAddress = dsProxyService.getProxyAddress("0xD71..........", true);
// Do something with dsProxyAddress

// 2 - set proxy actions allowance for 10 DAI
allowanceService.requireAllowance("0xD71..........",
                    mcd.getMcdConfiguration().getDssProxyActionsDsrAddress(),
                    TokenSymbols.DAI,
                    BigDecimal.TEN);

// 3 - join 3 DAI
TransactionReceipt receiptJoinThreeDai = savingsService.join(BigDecimal.valueOf(3));
// Do something with TransactionReceipt

// 4 - exit 1 DAI
TransactionReceipt receiptExitOneDai = savingsService.exit(BigDecimal.ONE);
// Do something with TransactionReceipt

// 5 - exit all DAI
TransactionReceipt receiptExitAllDai = savingsService.exitAll();
// Do something with TransactionReceipt

// 6 - remove proxy actions allowance
allowanceService.removeAllowance("0xD71..........",
                     mcd.getMcdConfiguration().getDssProxyActionsDsrAddress(),
                     TokenSymbols.DAI);
```

See McdTest.java for sample

