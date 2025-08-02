#!/bin/bash

FILE="progress.log"
BRANCH="main"
START_DATE="2025-04-24"
END_DATE="2025-07-14"

HIGH_ACTIVITY_END="2025-05-17"
BREAK_START="2025-06-07"
BREAK_END="2025-06-13"

# Convert to seconds
start=$(date -d "$START_DATE" +%s)
end=$(date -d "$END_DATE" +%s)

echo "" > $FILE  # Create/reset file

while [ $start -le $end ]; do
  day=$(date -d "@$start" +%Y-%m-%d)
  weekday=$(date -d "$day" +%u)

  # Skip weekends
  if [[ $weekday -gt 5 ]]; then
    start=$((start + 86400))
    continue
  fi

  # Skip June 7–13
  if [[ "$day" > "$BREAK_START" && "$day" < "$BREAK_END" ]]; then
    start=$((start + 86400))
    continue
  fi

  # Randomly skip some weekdays
  if (( RANDOM % 5 == 0 )); then
    start=$((start + 86400))
    continue
  fi

  # Decide commit count for the day
  if [[ "$day" < "$HIGH_ACTIVITY_END" ]]; then
    commit_count=$((RANDOM % 4 + 2))  # 2–5 commits
  else
    commit_count=$((RANDOM % 3))  # 0–2 commits
  fi

  for ((i=0; i<$commit_count; i++)); do
    # Random hour & minute between 9:00 and 22:00
    hour=$((RANDOM % 14 + 9))
    minute=$((RANDOM % 60))
    second=$((RANDOM % 60))

    timestamp="$day $(printf "%02d:%02d:%02d" $hour $minute $second)"

    echo "Work at $timestamp" >> $FILE

    GIT_AUTHOR_DATE="$timestamp" \
    GIT_COMMITTER_DATE="$timestamp" \
    git add $FILE && \
    git commit -m "Work on $timestamp"
  done

  # Move to next day
  start=$((start + 86400))
done

git push origin $BRANCH
