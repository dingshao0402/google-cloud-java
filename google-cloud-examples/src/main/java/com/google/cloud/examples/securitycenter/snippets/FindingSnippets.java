package com.google.cloud.examples.securitycenter.snippets;

import com.google.cloud.securitycenter.v1.Finding;
import com.google.cloud.securitycenter.v1.Finding.State;
import com.google.cloud.securitycenter.v1.FindingName;
import com.google.cloud.securitycenter.v1.ListFindingsRequest;
import com.google.cloud.securitycenter.v1.ListFindingsResponse.ListFindingsResult;
import com.google.cloud.securitycenter.v1.OrganizationName;
import com.google.cloud.securitycenter.v1.SecurityCenterClient;
import com.google.cloud.securitycenter.v1.SecurityCenterClient.ListFindingsPagedResponse;
import com.google.cloud.securitycenter.v1.SourceName;
import com.google.cloud.securitycenter.v1.UpdateFindingRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.protobuf.FieldMask;
import com.google.protobuf.Timestamp;
import com.google.protobuf.Value;
import java.io.IOException;
import java.util.ArrayList;
import org.threeten.bp.Instant;

/** Snippets for how to work with Findings in Cloud Security Command Center. */
public class FindingSnippets {
  private FindingSnippets() {}

  /**
   * Create a finding under a source.
   *
   * @param sourceName The source for the finding.
   */
  // [START create_finding]
  static Finding createFinding(SourceName sourceName, String findingId) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // SourceName sourceName = SourceName.of("123234324", "423432321");
      // String findingId = "samplefindingid";

      // Use the current time as the finding "event time".
      Instant eventTime = Instant.now();

      // The resource this finding applies to.  The CSCC UI can link
      // the findings for a resource to the corresponding Asset of a resource
      // if there are matches.
      String resourceName = "//cloudresourcemanager.googleapis.com/organizations/11232";

      // Start setting up a request to create a finding in a source.
      Finding finding = Finding.newBuilder()
          .setParent(sourceName.toString())
          .setState(State.ACTIVE).setResourceName(resourceName)
          .setEventTime(Timestamp.newBuilder()
              .setSeconds(eventTime.getEpochSecond())
              .setNanos(eventTime.getNano()))
          .setCategory("MEDIUM_RISK_ONE").build();

      // Call the API.
      Finding response = client.createFinding(sourceName, findingId, finding);

      System.out.println("Created Finding: " + response);
      return response;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END create_finding]

  /**
   * Create a finding with source properties under a source.
   *
   * @param sourceName The source for the finding.
   */
  // [START create_finding_with_source_properties]
  static Finding createFindingWithSourceProperties(SourceName sourceName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // SourceName sourceName = SourceName.of("123234324", "423432321");

      // Use the current time as the finding "event time".
      Instant eventTime = Instant.now();

      // Controlled by caller.
      String findingId = "samplefindingid2";

      // The resource this finding applies to.  The CSCC UI can link
      // the findings for a resource to the corresponding Asset of a resource
      // if there are matches.
      String resourceName = "//cloudresourcemanager.googleapis.com/organizations/11232";

      // Define source properties values as protobuf "Value" objects.
      Value stringValue = Value.newBuilder().setStringValue("stringExample").build();
      Value numValue = Value.newBuilder().setNumberValue(1234).build();
      ImmutableMap<String, Value> sourceProperties =
          ImmutableMap.of("stringKey", stringValue, "numKey", numValue);

      // Start setting up a request to create a finding in a source.
      Finding finding = Finding.newBuilder()
          .setParent(sourceName.toString())
          .setState(State.ACTIVE)
          .setResourceName(resourceName)
          .setEventTime(Timestamp.newBuilder()
              .setSeconds(eventTime.getEpochSecond())
              .setNanos(eventTime.getNano()))
          .putAllSourceProperties(sourceProperties).build();

      // Call the API.
      Finding response = client.createFinding(sourceName, findingId, finding);

      System.out.println("Created Finding with Source Properties: " + response);
      return response;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END create_finding_with_source_properties]

  /**
   * Update a finding under a source.
   *
   * @param findingName The finding to update.
   */
  // [START update_finding]
  static Finding updateFinding(FindingName findingName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // FindingName findingName = FindingName.of("123234324", "423432321", "samplefindingid2");

      // Use the current time as the finding "event time".
      Instant eventTime = Instant.now();

      // Define source properties values as protobuf "Value" objects.
      Value stringValue = Value.newBuilder().setStringValue("value").build();

      FieldMask updateMask = FieldMask.newBuilder()
          .addPaths("event_time")
          .addPaths("source_properties.stringKey").build();

      Finding finding = Finding.newBuilder()
          .setName(findingName.toString())
          .setEventTime(Timestamp.newBuilder()
              .setSeconds(eventTime.getEpochSecond())
              .setNanos(eventTime.getNano()))
          .putSourceProperties("stringKey", stringValue).build();

      UpdateFindingRequest.Builder request =
          UpdateFindingRequest.newBuilder().setFinding(finding).setUpdateMask(updateMask);

      // Call the API.
      Finding response = client.updateFinding(request.build());

      System.out.println("Updated Finding: " + response);
      return response;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END update_finding]

  /**
   * List all findings under an organization.
   *
   * @param organizationName The source to list all findings for.
   */
  // [START list_all_findings]
  static ImmutableList<ListFindingsResult> listAllFindings(OrganizationName organizationName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // OrganizationName organizationName = OrganizationName.of("123234324");
      SourceName sourceName = SourceName.of(organizationName.getOrganization(), "-");

      ListFindingsRequest.Builder request =
          ListFindingsRequest.newBuilder().setParent(sourceName.toString());

      // Call the API.
      ListFindingsPagedResponse response = client.listFindings(request.build());

      // This creates one list for all findings.  If your organization has a large number of findings
      // this can cause out of memory issues.  You can process them batches by returning
      // the Iterable returned response.iterateAll() directly.
      ImmutableList<ListFindingsResult> results = ImmutableList.copyOf(response.iterateAll());
      System.out.println("Findings:");
      System.out.println(results);
      return results;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END list_all_findings]

  /**
   * List filtered findings under a source.
   *
   * @param sourceName The source to list filtered findings for.
   */
  // [START list_filtered_findings]
  static ImmutableList<ListFindingsResult> listFilteredFindings(SourceName sourceName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // SourceName sourceName = SourceName.of("123234324", "423432321");

      // Create filter to category of MEDIUM_RISK_ONE
      String filter = "category=\"MEDIUM_RISK_ONE\"";

      ListFindingsRequest.Builder request =
          ListFindingsRequest.newBuilder().setParent(sourceName.toString()).setFilter(filter);

      // Call the API.
      ListFindingsPagedResponse response = client.listFindings(request.build());

      // This creates one list for all findings in the filter.If your organization has a large number of
      // findings this can cause out of memory issues.  You can process them batches by returning
      // the Iterable returned response.iterateAll() directly.
      ImmutableList<ListFindingsResult> results = ImmutableList.copyOf(response.iterateAll());
      System.out.println("Findings:");
      System.out.println(results);
      return results;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END list_filtered_findings]

  /**
   * List findings at a specific time under a source.
   *
   * @param sourceName The source to list findings at a specific time for.
   */
  // [START list_findings_at_time]
  static ImmutableList<ListFindingsResult> listFindingsAtTime(SourceName sourceName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // SourceName sourceName = SourceName.of("123234324", "423432321");

      // 5 days ago
      Instant fiveDaysAgo = Instant.now().minusSeconds(60*60*24*5);

      ListFindingsRequest.Builder request =
          ListFindingsRequest.newBuilder()
              .setParent(sourceName.toString())
              .setReadTime(Timestamp.newBuilder()
                  .setSeconds(fiveDaysAgo.getEpochSecond())
                  .setNanos(fiveDaysAgo.getNano()));

      // Call the API.
      ListFindingsPagedResponse response = client.listFindings(request.build());

      // This creates one list for all findings in the filter.If your organization has a large number of
      // findings this can cause out of memory issues.  You can process them batches by returning
      // the Iterable returned response.iterateAll() directly.
      ImmutableList<ListFindingsResult> results = ImmutableList.copyOf(response.iterateAll());
      System.out.println("Findings:");
      System.out.println(results);
      return results;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END list_findings_at_time]

  /**
   * Demonstrate calling testIamPermissions to determin if the service account has the correct
   * permissions.
   *
   * @param sourceName The source to create a finding for.
   */
  // [START test_iam_permissions]
  static TestIamPermissionsResponse testIamPermissions(SourceName sourceName) {
    try (SecurityCenterClient client = SecurityCenterClient.create()) {
      // SourceName sourceName = SourceName.of("123234324", "423432321");

      //Iam permission to test.
      ArrayList permissionsToTest = new ArrayList<>();
      permissionsToTest.add("securitycenter.findings.update");

      // Call the API.
      TestIamPermissionsResponse response =
          client.testIamPermissions(sourceName.toString(), permissionsToTest);
      System.out.println("IAM Permission:");
      System.out.println(response);

      return response;
    } catch (IOException e) {
      throw new RuntimeException("Couldn't create client.", e);
    }
  }
  // [END test_iam_permissions]
}
