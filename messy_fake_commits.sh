#!/bin/bash
#
# messy_fake_commits.sh
# Generates realistic-looking, back-dated commits between 2025-04-24 and 2025-07-14
#

FILE="progress.log"
START_DATE="2025-04-24"
END_DATE="2025-07-14"
HIGH_END="2025-05-17"
BREAK_START="2025-06-07"
BREAK_END="2025-06-13"

# Turn dates into seconds since epoch
start=$(date -d "$START_DATE" +%s)
end  =$(date -d "$END_DATE"   +%s)

# Reset the log file
echo "" > "$FILE"

while [ $start -le $end ]; do
  day=$(date -d "@$start" +%Y-%m-%d)
  dow=$(date -d "$day" +%u)     # 1=Mon…7=Sun

  # 1) skip weekends
  if [ $dow -gt 5 ]; then
    start=$((start + 86400))
    continue
  fi

  # 2) skip the June break
  if [[ "$day" > "$BREAK_START" && "$day" < "$BREAK_END" ]]; then
    start=$((start + 86400))
    continue
  fi

  # 3) randomly skip ~20% of weekdays
  if (( RANDOM % 5 == 0 )); then
    start=$((start + 86400))
    continue
  fi

  # 4) choose commit count
  if [[ "$day" < "$HIGH_END" ]]; then
    count=$(( RANDOM % 4 + 2 ))   # 2–5 commits/day early
  else
    count=$(( RANDOM % 3 ))       # 0–2 commits/day later
  fi

  for ((i=0; i<count; i++)); do
    # random time between 09:00–22:59
    h=$(( RANDOM % 14 + 9 ))
    m=$(( RANDOM % 60 ))
    s=$(( RANDOM % 60 ))
    ts="$day $(printf "%02d:%02d:%02d" $h $m $s)"

    echo "Work at $ts" >> "$FILE"
    git add "$FILE"
    git commit --date="$ts" -m "Work on $ts"
  done

  start=$((start + 86400))
done

# push your master branch
git push
