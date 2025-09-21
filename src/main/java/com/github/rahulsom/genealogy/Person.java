package com.github.rahulsom.genealogy;

import lombok.Builder;

/**
 * Created by rahulsomasunderam on 5/31/14.
 *
 * @param firstName The person's first name.
 * @param lastName The person's last name.
 * @param race The person's race.
 * @param gender The person's gender.
 */
@Builder
public record Person(String firstName, String lastName, Race race, String gender) {}
