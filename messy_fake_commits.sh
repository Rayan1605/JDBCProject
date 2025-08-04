#!/bin/bash

FILE="progress.log"
BRANCH="master"             # ← your branch
START_DATE="2025-04-24"
END_DATE="2025-07-14"

HIGH_ACTIVITY_END="2025-05-17"
BREAK_START="2025-06-07"
BREAK_END="2025-06-13"

# convert to seconds
start=$(date -d "$START_DATE" +%s)
end  =$(date -d "$END_DATE"   +%s)

# reset the dummy file
echo "" > "$FILE"

while [ $start -le $end ]; do
  day=$(date -d "@$start" +%Y-%m-%d)
  weekday=$(date -d "$day" +%u)

  # skip weekends
  if [[ $weekday -gt 5 ]]; then
    start=$((start + 86400))
    continue
  fi

  # skip break week
  if [[ "$day" > "$BREAK_START" && "$day" < "$BREAK_END" ]]; then
    start=$((start + 86400))
    continue
  fi

  # randomly skip ~20% of days
  if (( RANDOM % 5 == 0 )); then
    start=$((start + 86400))
    continue
  fi

  # pick 2–5 commits early, 0–2 commits later
  if [[ "$day" < "$HIGH_ACTIVITY_END" ]]; then
    commit_count=$(( RANDOM % 4 + 2 ))
  else
    commit_count=$(( RANDOM % 3 ))
  fi

  for ((i=0; i<commit_count; i++)); do
    hour=$(( RANDOM % 14 + 9 ))
    minute=$(( RANDOM % 60 ))
    second=$(( RANDOM % 60 ))
    timestamp="$day $(printf "%02d:%02d:%02d" $hour $minute $second)"

    # stage updates
    echo "Work at $timestamp" >> "$FILE"
    git add "$FILE"

    # **apply fake date to the commit itself**
    GIT_AUTHOR_DATE="$timestamp" GIT_COMMITTER_DATE="$timestamp" \
      git commit -m "Work on $timestamp"
  done

  # next day
  start=$((start + 86400))
done

# push to master
git push origin $BRANCH
