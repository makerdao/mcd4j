# mcd4j

Java library for interacting with MCD

### Prerequisite:
- java
- maven

### Build:
    mvn clean
    mvn package -Dmaven.test.skip=true

For running tests on kovan:  
    `mvn -DprivateKey=71F1EA.. -DkovanEndpoint=https://localhost:8545/ package`

For running tests on mainnet:  
    `mvn -DprivateKey=71F1EA.. -DmainnetEndpoint=https://localhost:8545/ package`
    

### Usage:

```java
# Instantiate Mcd object
Mcd mcd = new Mcd(web3j, credentials, gasProvider);

# get saving service
SavingsService savingsService = mcdMainnet.getSavingsService()

# get total DAI
BigDecimal totalDai = savingsService.getTotalDai()
```

See McdTest.java for sample

