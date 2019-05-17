package io.pivotal.cfapp.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({ "foundations", "total-organizations", "total-spaces", "total-user-accounts", "total-service-accounts" })
public class Demographics {

    @Default
    @JsonProperty("foundations")
    private Set<String> foundations = new HashSet<>();

    @Default
    @JsonProperty("total-organizations")
    private Long organizations = 0L;

    @Default
    @JsonProperty("total-spaces")
    private Long spaces = 0L;

    @Default
    @JsonProperty("total-user-accounts")
    private Long userAccounts = 0L;

    @Default
    @JsonProperty("total-service-accounts")
    private Long serviceAccounts = 0L;

    @JsonCreator
    public Demographics(
        @JsonProperty("foundations") Set<String> foundations,
        @JsonProperty("total-organizations") Long organizations,
        @JsonProperty("total-spaces") Long spaces,
        @JsonProperty("total-user-accounts") Long userAccounts,
        @JsonProperty("total-service-accounts") Long serviceAccounts
    ) {
        this.foundations = foundations;
        this.organizations = organizations;
        this.spaces = spaces;
        this.userAccounts = userAccounts;
        this.serviceAccounts = serviceAccounts;
    }
}