// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/cloud/talent/v4beta1/event.proto

package com.google.cloud.talent.v4beta1;

public interface JobEventOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.cloud.talent.v4beta1.JobEvent)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required.
   * The type of the event (see
   * [JobEventType][google.cloud.talent.v4beta1.JobEvent.JobEventType]).
   * </pre>
   *
   * <code>.google.cloud.talent.v4beta1.JobEvent.JobEventType type = 1;</code>
   */
  int getTypeValue();
  /**
   *
   *
   * <pre>
   * Required.
   * The type of the event (see
   * [JobEventType][google.cloud.talent.v4beta1.JobEvent.JobEventType]).
   * </pre>
   *
   * <code>.google.cloud.talent.v4beta1.JobEvent.JobEventType type = 1;</code>
   */
  com.google.cloud.talent.v4beta1.JobEvent.JobEventType getType();

  /**
   *
   *
   * <pre>
   * Required.
   * The [job name(s)][google.cloud.talent.v4beta1.Job.name] associated with
   * this event. For example, if this is an
   * [impression][google.cloud.talent.v4beta1.JobEvent.JobEventType.IMPRESSION]
   * event, this field contains the identifiers of all jobs shown to the job
   * seeker. If this was a
   * [view][google.cloud.talent.v4beta1.JobEvent.JobEventType.VIEW] event, this
   * field contains the identifier of the viewed job.
   * </pre>
   *
   * <code>repeated string jobs = 2;</code>
   */
  java.util.List<java.lang.String> getJobsList();
  /**
   *
   *
   * <pre>
   * Required.
   * The [job name(s)][google.cloud.talent.v4beta1.Job.name] associated with
   * this event. For example, if this is an
   * [impression][google.cloud.talent.v4beta1.JobEvent.JobEventType.IMPRESSION]
   * event, this field contains the identifiers of all jobs shown to the job
   * seeker. If this was a
   * [view][google.cloud.talent.v4beta1.JobEvent.JobEventType.VIEW] event, this
   * field contains the identifier of the viewed job.
   * </pre>
   *
   * <code>repeated string jobs = 2;</code>
   */
  int getJobsCount();
  /**
   *
   *
   * <pre>
   * Required.
   * The [job name(s)][google.cloud.talent.v4beta1.Job.name] associated with
   * this event. For example, if this is an
   * [impression][google.cloud.talent.v4beta1.JobEvent.JobEventType.IMPRESSION]
   * event, this field contains the identifiers of all jobs shown to the job
   * seeker. If this was a
   * [view][google.cloud.talent.v4beta1.JobEvent.JobEventType.VIEW] event, this
   * field contains the identifier of the viewed job.
   * </pre>
   *
   * <code>repeated string jobs = 2;</code>
   */
  java.lang.String getJobs(int index);
  /**
   *
   *
   * <pre>
   * Required.
   * The [job name(s)][google.cloud.talent.v4beta1.Job.name] associated with
   * this event. For example, if this is an
   * [impression][google.cloud.talent.v4beta1.JobEvent.JobEventType.IMPRESSION]
   * event, this field contains the identifiers of all jobs shown to the job
   * seeker. If this was a
   * [view][google.cloud.talent.v4beta1.JobEvent.JobEventType.VIEW] event, this
   * field contains the identifier of the viewed job.
   * </pre>
   *
   * <code>repeated string jobs = 2;</code>
   */
  com.google.protobuf.ByteString getJobsBytes(int index);
}
