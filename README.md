# mcd4j

Java library for interacting with MCD

### Prerequisite:
- java
- maven

### Build:
    mvn clean
    mvn package -Dmaven.test.skip=true
    
##### Install in local maven repository:
    mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=target/mcd4j-1.0-SNAPSHOT.jar

##### Use in pom.xml:
        <dependency>
            <groupId>org.makerdao</groupId>
            <artifactId>mcd4j</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>


### Usage:

```java
String registeredEthAccount = "0xD71..........";

// Instantiate Mcd object
Mcd mcd = new Mcd(web3j, credentials, gasProvider);

// get services
SavingsService savingsService = mcd.getSavingsService();
DSProxyService dsProxyService = mcd.getDSProxyService();
AllowanceService allowanceService = mcd.getAllowanceService();

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
BigDecimal myDsrBalance = savingsService.getBalanceOf(dsProxy.getContractAddress());

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

See examples/DsrExample.java for an example

### Testing

Prerequisites:
* dai.js testchain started (see https://github.com/makerdao/dai.js#running-the-testchain)
* testchain account `0x16fb96a5fa0427af0c8f7cf1eb4870231c8154b6` funded with 50 DAI or more (see https://github.com/makerdao/dai.js/blob/dev/packages/dai-plugin-mcd/test/SavingsService.spec.js#L41 for how to open a Vault and generate DAI)

For running tests on testchain:  `mvn test`