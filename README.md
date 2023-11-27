Java 17 is required to build.
The secrets.defaults.properties file should not normally contain real keys.

### Architecture:
Architecture is based on a multi-module approach (similar to the NowInAndroid project) with convention gradle plugins and a toml version catalog.
The app uses pagination based on the Pagination 3 library. The local cache is implemented using Room.

### Tech stack
- MVVM + Single UiState + StateIn (WhileSubscribed) + StateInMerge extension
- Ktor + kotlinX Serialization + Room
- Arrow Either
- Compose + Compose Navigation + Coil
- Hilt/Dagger for DI
- Ktlint and detekt
- Junit 5 + Mockk + Kluent 

### Tests:
Tests are incomplete. I've implemented only a few to demonstrate the usage of mocks and the general approach to testing suspended functions and flows / StateFlows.
I didn't have time to implement any tests based on fakes or UI tests.
