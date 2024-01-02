package com.sistemasronald.springboot.backend.apirest.auth;

public class JwtConfig {

    public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";

    public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAuxuNq15rRyynDazgimrL8DKBEJufVt2jlKC40NFc41bI/cYZ\n" +
            "ecPfm5yDehknAal2TXw+UAMnJYT8Tyh80LiWSkkiNrtbKsRG6Rx/sQKS0m20D7j5\n" +
            "u5sjLxe1Dt/QBGV4rgdnILZDDijE1GZhy69qzVO1l80cmIyN9IxVqxdP3Lol/aUb\n" +
            "wYwI2yCcbvS27bJrXCDXW3GRk+9zYnoi0h2bfsoEfAsBiCjMUMyvYKxagnBUKSZR\n" +
            "UseT58tFxKO25XT2gRpOBQnxnBQ4x7xzp8Ft7qI2aqHgnvPU0GFF21oNanZsft7G\n" +
            "ri1foG5Z+SxJZndwdi4lGwFP47o37VRqADvX8wIDAQABAoIBAApH5EvjPz/E61Ts\n" +
            "B9ZzMct76u2Pr3ylZwSWpDENH+556UdQaDvxkErLmcnOsf78Yby2M/JPigocuYRb\n" +
            "9Ce/zLo+NHwPZV3/NOpAH6AJRSn8zyUCA+7UAmXnBpLPp21mafAeBgOdICgaA16s\n" +
            "VXlMpTCtBdBWe9jB+FbUkyx4dnHpTHmm808EOifx879MkdxlvPp+IQ+V8s3/0Uwd\n" +
            "HV9HIuSo04RjJnZXjyAX9kJxQux13eAb3wZ66SZl+G9aX281WFqV5e2cjvMuu4eX\n" +
            "0loUgqcOAADu4CWVtlO5QGGcihIx+MLap2yoMN6oekfzNay3B1zMAwyZ8phkPEIr\n" +
            "VMDkPiECgYEA3Qy4/ElH5E4VBiFRJBcu5dkX5wWJCNV1mym3wvcxPNbI+yFjcvW4\n" +
            "QquBN+X/VslT+0wYHIqgvHt2rhaq8ASfRBVDIsVrQQLZpOeo5XCpHIbtWkCRVNY/\n" +
            "3MGLAiyhjeXXN599JOAJigC5lzqAsb6vB9u2P1qRjlDcHPmqyOkXABECgYEA2LD7\n" +
            "IEEPqkL12uN6Ha6Ej6lYc2CSRceaPb68uUKcBjuuRg0L4KXGYgBbS4QYDVcXNGU2\n" +
            "pcrJYfVQn4w3kM643W+7jIlOmd4jkILHkBIQ8YilgAjfoSZXIUKM5ADWPKPT9Z0Y\n" +
            "PvdUSAVFXWjBzLHmHongoEB46bdZeULjOABlG8MCgYEAreBXFmQSP9zHntXQPKJb\n" +
            "yoL00U1CWIeIBfBP0vWcbm3JE/lUf9YXBaJgd3bcXgjpnY9im/jTrM81MIU2GqyY\n" +
            "Fvbeo1hFIJM8XgG3khsHeyALTeXiK6iH/X7keGZhAfwcSW2UA9CS0FH3qqRjHO2Q\n" +
            "Qo8qETWNrzOdoNEm7PiklfECgYBmXbVE13d/B4L9lNjrjLYy591RJB31uQW0hZbC\n" +
            "LiVnDGqXzTH/PlEpmuGGGzEBnFLp/aaDS/k2vZ0IwkS9s+rBMTd4fKUVK0vByQZd\n" +
            "mStH7zW/9nEuv94JaFcGnx6WJw7KyRvbIxw9qb/LEOH8wU3kv13OVqbIQkWSc3bD\n" +
            "zev14QKBgB6KF/CYFwZ8rvkRZO7rKTBQ/3Nh75LHHMheCMORwanyY1vYtw4aTEOV\n" +
            "X3GGqgT6Rc4X2oSXNRnWf1+zkguDGd7mEH73ektbJ+ajFAxVgK0kVR6ehyUWzwFU\n" +
            "wRIRx5mNy6b1oxK677/tgQAO2sJOC1Lv1RgjR4+g/MAPIytC37ld\n" +
            "-----END RSA PRIVATE KEY-----";

    public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuxuNq15rRyynDazgimrL\n" +
            "8DKBEJufVt2jlKC40NFc41bI/cYZecPfm5yDehknAal2TXw+UAMnJYT8Tyh80LiW\n" +
            "SkkiNrtbKsRG6Rx/sQKS0m20D7j5u5sjLxe1Dt/QBGV4rgdnILZDDijE1GZhy69q\n" +
            "zVO1l80cmIyN9IxVqxdP3Lol/aUbwYwI2yCcbvS27bJrXCDXW3GRk+9zYnoi0h2b\n" +
            "fsoEfAsBiCjMUMyvYKxagnBUKSZRUseT58tFxKO25XT2gRpOBQnxnBQ4x7xzp8Ft\n" +
            "7qI2aqHgnvPU0GFF21oNanZsft7Gri1foG5Z+SxJZndwdi4lGwFP47o37VRqADvX\n" +
            "8wIDAQAB\n" +
            "-----END PUBLIC KEY-----";

}
